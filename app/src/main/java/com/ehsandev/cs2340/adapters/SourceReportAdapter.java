package com.ehsandev.cs2340.adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.model.SourceReport;

import java.text.DecimalFormat;

public class SourceReportAdapter extends ArrayAdapter<SourceReport>{
    private SourceReport[] items;
    private Context context;
    private int textViewResourceId;

    public SourceReportAdapter (Context context, int textViewResourceId, SourceReport[] items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
        this.textViewResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("#.##");

        View v = convertView;
        SourceReport s = getItem(position);

        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        v = inflater.inflate(textViewResourceId, parent, false);
        v.setTag(s);
        v.setId(s.getId().hashCode());

        TextView id = (TextView) v.findViewById(R.id.sourceId);
        TextView loc = (TextView) v.findViewById(R.id.sourceLoc);
        id.setText("Type:" + s.getType() + ", Condition: " + s.getCondition());
        loc.setText(df.format(s.getLat()) + "," + df.format(s.getLon()));
        return v;
    }
}