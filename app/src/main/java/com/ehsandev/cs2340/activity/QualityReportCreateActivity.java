package com.ehsandev.cs2340.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.model.QualityReport;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.SourceReport;
import com.ehsandev.cs2340.task.QualityReportSubmitTask;
import com.ehsandev.cs2340.task.SourceReportSubmitTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class QualityReportCreateActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, QualityReportSubmitTask.AsyncTaskCompleteListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap m;
    private GoogleApiClient mGoogleApiClient;
    private LatLng loc;
    private Spinner conditions;
    private Spinner types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualityreport_create);
        //Define Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Configure Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        conditions = (Spinner) findViewById(R.id.qualityReportConditionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.conditionsQuality, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditions.setAdapter(adapter);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        m = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else {
            // Create an instance of GoogleAPIClient.
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                mGoogleApiClient.connect();
                m.setMyLocationEnabled(true);
                m.getUiSettings().setMyLocationButtonEnabled(true);
                m.getUiSettings().setAllGesturesEnabled(false);
            }
        }

    }
    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(Bundle connectionHint) {

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
            loc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions mo = new MarkerOptions().position(loc);
            m.addMarker(mo);
            m.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,16));

        }

    }
    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();
                    mGoogleApiClient.connect();
                    m.setMyLocationEnabled(true);

                } else {

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDone(Response<String> s) {
        if (s.getSuccess() == 1){
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            EditText virus = (EditText) findViewById(R.id.qualityReportVirusEdit);
            EditText cont = (EditText) findViewById(R.id.qualityReportContEdit);
            new QualityReportSubmitTask(this, this, sp.getString("cookie", null)).execute(
                    new QualityReport("report", loc.latitude, loc.longitude,
                            conditions.getSelectedItem().toString(),
                            Integer.parseInt(virus.getText().toString()),
                            Integer.parseInt(cont.getText().toString())));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
