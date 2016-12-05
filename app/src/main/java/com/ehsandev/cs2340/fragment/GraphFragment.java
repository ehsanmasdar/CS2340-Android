package com.ehsandev.cs2340.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.ehsandev.cs2340.model.QualityReport;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.task.QualityReportTask;

public class GraphFragment extends Fragment implements QualityReportTask.AsyncTaskCompleteListener {
    private QualityReport[] reports;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getContext());
        new QualityReportTask(this, getContext()).execute(s.getString("cookie", null));
    }

    @Override
    public void onGotData(Response<QualityReport[]> s) {
        reports = s.getData();
    }
}
