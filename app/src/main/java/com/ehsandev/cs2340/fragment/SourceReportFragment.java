package com.ehsandev.cs2340.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.activity.SourceReportViewActivity;
import com.ehsandev.cs2340.adapters.SourceReportAdapter;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.SourceReport;
import com.ehsandev.cs2340.task.SourceReportTask;


public class SourceReportFragment extends ListFragment implements SourceReportTask.AsyncTaskCompleteListener {
    private SourceReport[] reports;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getContext());
        new SourceReportTask(this, getContext()).execute(s.getString("cookie", null));
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getContext());
        new SourceReportTask(this, getContext()).execute(s.getString("cookie", null));
    }
    @Override
    public void onGotData(Response<SourceReport[]> s) {
        SourceReportAdapter a = new SourceReportAdapter(getActivity(), R.layout.source_row, s.getData());
        setListAdapter(a);
        reports = s.getData();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), SourceReportViewActivity.class);
        i.putExtra("report", reports[position]);
        startActivity(i);
    }
}
