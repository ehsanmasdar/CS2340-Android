package com.ehsandev.cs2340.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ehsandev.cs2340.api.ReportHandler;
import com.ehsandev.cs2340.api.UserHandler;
import com.ehsandev.cs2340.model.Profile;
import com.ehsandev.cs2340.model.QualityReport;
import com.ehsandev.cs2340.model.Response;



public class ProfileTask extends AsyncTask<String, Response<Profile>, Response<Profile>> {
    private AsyncTaskCompleteListener callback;
    private ProgressDialog dialog;
    private Context context;

    public ProfileTask(AsyncTaskCompleteListener callback, Context context) {
        this.callback = callback;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog == null) {
            this.dialog = new ProgressDialog(context);
            this.dialog.setMessage("Loading Profile ....");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }
    }
    @Override
    protected Response<Profile> doInBackground(String... cookie) {
        return UserHandler.getProfile(cookie[0]);
    }

    @Override
    protected void onPostExecute(Response<Profile> r) {
        dialog.dismiss();
        callback.onGotData(r);
    }
    public interface AsyncTaskCompleteListener {
        public void onGotData(Response<Profile> s);
    }
}