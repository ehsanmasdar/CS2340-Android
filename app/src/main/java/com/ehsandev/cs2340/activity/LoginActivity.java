package com.ehsandev.cs2340.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.User;
import com.ehsandev.cs2340.task.LoginTask;


public class LoginActivity extends AppCompatActivity implements LoginTask.AsyncTaskCompleteListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        if (p.contains("cookie")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onLoginClick(View v) {
        EditText username = (EditText) findViewById((R.id.edittext_login_username));
        EditText password = (EditText) findViewById(R.id.edittext_login_password);
        login(username.getText().toString(), password.getText().toString());
    }
    public void login (String username, String password){
        new LoginTask(this, this).execute(new User(username,password));
    }

    @Override
    public void onLoginDone(Response<String> r) {
        if (r.getSuccess() == 1) {
            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor e = p.edit();
            e.putString("cookie", r.getData());
            Log.d("debug", "setting cookie to " + r.getData());
            e.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}