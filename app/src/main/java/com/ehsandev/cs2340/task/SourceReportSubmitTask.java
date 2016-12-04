package com.ehsandev.cs2340.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ehsandev.cs2340.api.ReportHandler;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.SourceReport;

public class SourceReportSubmitTask extends AsyncTask<SourceReport, Response<String>, Response<String>> {
    private AsyncTaskCompleteListener callback;
    private ProgressDialog dialog;
    private Context context;
    private String cookie;

    public SourceReportSubmitTask(AsyncTaskCompleteListener callback, Context context, String cookie) {
        this.callback = callback;
        this.context = context;
        this.cookie = cookie;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog == null) {
            this.dialog = new ProgressDialog(context);
            this.dialog.setMessage("Submitting Report ....");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }
    }
    @Override
    protected Response<String> doInBackground(SourceReport... reports) {
        return ReportHandler.postSourceReport(reports[0], cookie);
    }

    @Override
    protected void onPostExecute(Response<String> r) {
        dialog.dismiss();
        callback.onDone(r);
    }
    public interface AsyncTaskCompleteListener {
        public void onDone(Response<String> s);
    }

}