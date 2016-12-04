package com.ehsandev.cs2340.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ehsandev.cs2340.model.SourceReport;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {
    private final Context context;
    private SourceReport[] sourceReports;

    public MarkerAdapter(SourceReport[] sourceReports, Context context){
        this.sourceReports = sourceReports;
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        for (SourceReport s : sourceReports) {
            if (s.getId().equals(marker.getTitle())) {
                TextView t = new TextView(context);
                t.setText("Report for " + s.getLat() + "," + s.getLon() + "\r\n"
                        + "Condition: " + s.getCondition() + "\r\n"
                        + "Type: " + s.getType());
                return t;

            }
        }
        return null;
    }
}
