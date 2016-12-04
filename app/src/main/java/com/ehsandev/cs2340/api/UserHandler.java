package com.ehsandev.cs2340.api;

import android.util.Log;

import com.ehsandev.cs2340.model.AccessLevel;
import com.ehsandev.cs2340.model.Profile;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.User;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.http.HttpResponse;
import org.json.JSONObject;

public class UserHandler {
    /**
     * Post user registration to API Endpoint
     * @param user User registration to create
     * @return API response status
     */
    public static Response<String> postRegister(User user){
        try {
            JSONObject jsonResponse = Unirest.post(Constants.URL_BASE + "/api/user/register")
                    .field("username", user.getUsername())
                    .field("password", user.getPassword())
                    .field("level", user.getLevel()).asJson().getBody().getObject();
            if (jsonResponse.has("message")){
                return new Response<>(jsonResponse.getInt("success"), jsonResponse.getString("message"), null);
            }
            else{
                return new Response<>(jsonResponse.getInt("success"), "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(0,"",null);
        }
    }

    /**
     * Post user login request to API Endpoint
     * @param user User to login
     * @return API Response status
     */
    public static Response<String> postLogin(User user){
        try {
            MultipartBody resp = Unirest.post(Constants.URL_BASE + "/api/user/login")
                    .field("username", user.getUsername())
                    .field("password", user.getPassword());
            JSONObject jsonResponse = resp.asJson().getBody().getObject();
            HttpResponse response = resp.asString();
            String cookie = response.getHeaders().getFirst("set-cookie").split(";")[0] + ";";
            if (jsonResponse.has("message")){
                return new Response<>(jsonResponse.getInt("success"), jsonResponse.getString("message"), cookie);
            }
            else{
                return new Response<>(jsonResponse.getInt("success"), "", cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(0,"",null);
        }
    }

    /**
     * Create or update user profile
     * @param p Updated profile object
     * @param cookie Authentication cookie
     * @return API Response status
     */
    public static Response<String> postProfile(Profile p, String cookie){
        try {
            JSONObject jsonResponse = Unirest.post(Constants.URL_BASE + "/api/user/profile").header("Cookie", cookie)
                    .field("firstname", p.getName())
                    .field("lastname", "empty")
                    .field("address", p.getAddress())
                    .field("email", p.getEmail())
                    .asJson().getBody().getObject();
            if (jsonResponse.has("message")){
                return new Response<>(jsonResponse.getInt("success"), jsonResponse.getString("message"), null);
            }
            else{
                return new Response<>(jsonResponse.getInt("success"), "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(0,"",null);
        }
    }

    /**
     * Get user profile
     * @param cookie Authentication cookie
     * @return API Response with user profile if successful
     */
    public static Response<Profile> getProfile(String cookie){
        try {
            JSONObject jsonResponse = Unirest.get(Constants.URL_BASE + "/api/user/profile").header("Cookie", cookie).asJson().getBody().getObject();
            if (jsonResponse.getInt("success") == 1){
                JSONObject data = jsonResponse.getJSONObject("data");
                if (data.has("firstname"))
                    return new Response<>(jsonResponse.getInt("success"), "", new Profile(data.getString("firstname"), data.getString("address"), data.getString("email")));
                return new Response<>(jsonResponse.getInt("success"), "", new Profile("","",""));
            }
            else{
                System.out.println(jsonResponse.getString("message"));
                return new Response<>(jsonResponse.getInt("success"), "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(0,"",null);
        }
    }

    /**
     * Get user object
     * @param cookie Authentication cookie
     * @return API Response with user object if successful
     */
    public static Response<User> getUser(String cookie){
        try {
            JSONObject jsonResponse = Unirest.get(Constants.URL_BASE + "/api/user/").header("Cookie", cookie).asJson().getBody().getObject();
            if (jsonResponse.getInt("success") == 1){
                JSONObject user = jsonResponse.getJSONObject("user");
                return new Response<>(jsonResponse.getInt("success"), "", new User(user.getString("username"), AccessLevel.valueOf(user.getString("level").toUpperCase())));
            }
            else{
                System.out.println(jsonResponse.getString("message"));
                return new Response<>(jsonResponse.getInt("success"), "", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(0,"",null);
        }
    }
}
