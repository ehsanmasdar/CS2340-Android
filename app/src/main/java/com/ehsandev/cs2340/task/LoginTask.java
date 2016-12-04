package com.ehsandev.cs2340.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.ehsandev.cs2340.api.UserHandler;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.User;

public class LoginTask extends AsyncTask<User, Response<String>, Response<String>> {
    private AsyncTaskCompleteListener callback;
    private ProgressDialog dialog;
    private Context context;

    public LoginTask(AsyncTaskCompleteListener callback, Context context) {
        this.callback = callback;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog == null) {
            this.dialog = new ProgressDialog(context);
            this.dialog.setMessage("Logging In....");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }
    }
    @Override
    protected Response doInBackground(User... user) {
        Response<String> r = UserHandler.postLogin(user[0]);
        if (r.getSuccess() == 1){
            Response<User> userInfo = UserHandler.getUser(r.getData());
            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor e = p.edit();
            e.putString("level", userInfo.getData().getLevel().toString());
            e.commit();
        }
        return r;
    }

    @Override
    protected void onPostExecute(Response<String> r) {
        dialog.dismiss();
        callback.onLoginDone(r);
    }
    public interface AsyncTaskCompleteListener {
        public void onLoginDone(Response<String> s);
    }

}