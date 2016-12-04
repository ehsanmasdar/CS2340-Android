package com.ehsandev.cs2340.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.activity.QualityReportViewActivity;
import com.ehsandev.cs2340.adapters.QualityReportAdapter;
import com.ehsandev.cs2340.model.QualityReport;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.task.QualityReportTask;


public class QualityReportFragment extends ListFragment implements QualityReportTask.AsyncTaskCompleteListener {
    private QualityReport[] reports;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getContext());
        new QualityReportTask(this, getContext()).execute(s.getString("cookie", null));
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getContext());
        new QualityReportTask(this, getContext()).execute(s.getString("cookie", null));
    }
    @Override
    public void onGotData(Response<QualityReport[]> s) {
        QualityReportAdapter a = new QualityReportAdapter(getActivity(), R.layout.source_row, s.getData());
        setListAdapter(a);
        reports = s.getData();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), QualityReportViewActivity.class);
        i.putExtra("report", reports[position]);
        startActivity(i);
    }
}
