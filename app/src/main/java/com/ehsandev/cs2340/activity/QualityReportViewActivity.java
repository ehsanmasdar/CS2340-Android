package com.ehsandev.cs2340.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.model.QualityReport;
import com.ehsandev.cs2340.model.SourceReport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;


public class QualityReportViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private QualityReport s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualityreport_view);
        //Define Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Configure Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        Intent i = getIntent();

        s = (QualityReport) i.getSerializableExtra("report");
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        TextView virus = (TextView) findViewById(R.id.qualityReportVirus);
        TextView cont = (TextView) findViewById(R.id.qualityReportCont);
        TextView condition = (TextView) findViewById(R.id.qualityReportCondition);
        TextView id = (TextView) findViewById(R.id.qualityReportId);
        TextView time = (TextView) findViewById(R.id.qualityReportTime);
        virus.setText("Virus PPM: " + s.getVirus());
        cont.setText("Contaminant PPM: " + s.getContaminant());
        condition.setText("Condition: " + s.getCondition());
        id.setText("ID: " + s.getId());
        time.setText("Time: " + s.getDate());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DecimalFormat df = new DecimalFormat("#.##");

        Marker m = googleMap.addMarker(new MarkerOptions().position(new LatLng(s.getLat(), s.getLon())).title(df.format(s.getLat()) + ", " + df.format(s.getLon())));
        m.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(s.getLat(), s.getLon()), 7));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }
}
