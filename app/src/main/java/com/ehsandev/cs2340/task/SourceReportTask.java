package com.ehsandev.cs2340.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ehsandev.cs2340.api.ReportHandler;
import com.ehsandev.cs2340.api.UserHandler;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.SourceReport;
import com.ehsandev.cs2340.model.User;

public class SourceReportTask extends AsyncTask<String, Response<SourceReport[]>, Response<SourceReport[]>> {
    private AsyncTaskCompleteListener callback;
    private ProgressDialog dialog;
    private Context context;

    public SourceReportTask(AsyncTaskCompleteListener callback, Context context) {
        this.callback = callback;
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog == null) {
            this.dialog = new ProgressDialog(context);
            this.dialog.setMessage("Getting Reports ....");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }
    }
    @Override
    protected Response<SourceReport[]> doInBackground(String... cookie) {
        return ReportHandler.getSourceReports(cookie[0]);
    }

    @Override
    protected void onPostExecute(Response<SourceReport[]> r) {
        dialog.dismiss();
        callback.onGotData(r);
    }
    public interface AsyncTaskCompleteListener {
        public void onGotData(Response<SourceReport[]> s);
    }

}