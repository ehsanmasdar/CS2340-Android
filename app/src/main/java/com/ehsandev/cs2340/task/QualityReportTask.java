package com.ehsandev.cs2340.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.ehsandev.cs2340.api.ReportHandler;
import com.ehsandev.cs2340.model.QualityReport;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.SourceReport;

public class QualityReportTask extends AsyncTask<String, Response<QualityReport[]>, Response<QualityReport[]>> {
    private AsyncTaskCompleteListener callback;
    private ProgressDialog dialog;
    private Context context;

    public QualityReportTask(AsyncTaskCompleteListener callback, Context context) {
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
    protected Response<QualityReport[]> doInBackground(String... cookie) {
        return ReportHandler.getPurityReports(cookie[0]);
    }

    @Override
    protected void onPostExecute(Response<QualityReport[]> r) {
        dialog.dismiss();
        callback.onGotData(r);
    }
    public interface AsyncTaskCompleteListener {
        public void onGotData(Response<QualityReport[]> s);
    }

}