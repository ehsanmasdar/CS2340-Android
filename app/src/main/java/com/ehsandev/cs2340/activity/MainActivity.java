package com.ehsandev.cs2340.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.adapters.MarkerAdapter;
import com.ehsandev.cs2340.fragment.QualityReportFragment;
import com.ehsandev.cs2340.fragment.SourceReportFragment;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.SourceReport;
import com.ehsandev.cs2340.task.SourceReportTask;
import com.ehsandev.cs2340.util.Access;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, SourceReportTask.AsyncTaskCompleteListener {
    private GoogleMap googleMap;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                if (sp.getInt("fragment", 0) == 1){
                    Intent i = new Intent(getApplicationContext(), SourceReportCreateActivity.class);
                    startActivity(i);
                }
                else if (sp.getInt("fragment", 0) == 2){
                    Intent i = new Intent(getApplicationContext(), QualityReportCreateActivity.class);
                    startActivity(i);
                }

            }
        });
        fab.setVisibility(View.INVISIBLE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(sp.getInt("fragment", 0)));
        if (!Access.isHigherThanUser(this)){
            navigationView.getMenu().findItem(R.id.nav_quality).setVisible(false);
        }
        if (!Access.isHigherThanWorker(this)) {
            navigationView.getMenu().findItem(R.id.nav_graph).setVisible(false);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        View hView =  navigationView.getHeaderView(0);
        if (sp.contains("name") && sp.contains("email")){
            TextView t = (TextView) hView.findViewById(R.id.fullname);
            TextView e = (TextView) hView.findViewById(R.id.email);
            t.setText(sp.getString("name", "Unknown Name"));
            e.setText(sp.getString("email", "Unknown Email"));
        }
    }
    public void updateNavbar(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        View hView =  navigationView.getHeaderView(0);
        if (sp.contains("name") && sp.contains("email")){
            TextView t = (TextView) hView.findViewById(R.id.fullname);
            TextView e = (TextView) hView.findViewById(R.id.email);
            t.setText(sp.getString("name", "Unknown Name"));
            e.setText(sp.getString("email", "Unknown Email"));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor e = sp.edit();
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();
        item.setChecked(true);
        if (id == R.id.nav_map)  {
            setTitle("Map");
            e.putInt("fragment", 0);
            fab.setVisibility(View.INVISIBLE);
            SupportMapFragment mapFragment = new SupportMapFragment();
            mapFragment.getMapAsync(this);
            fragmentManager.beginTransaction().replace(R.id.content_frame, mapFragment).commit();
        } else if (id == R.id.nav_source) {
            setTitle("Source Reports");
            e.putInt("fragment", 1);
            fab.setVisibility(View.VISIBLE);
            SourceReportFragment sourceFragment = new SourceReportFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, sourceFragment).commit();
        } else if (id == R.id.nav_quality) {
            fab.setVisibility(View.VISIBLE);
            if (Access.isHigherThanWorker(this)){
                e.putInt("fragment", 2);
                QualityReportFragment qualityReportFragment = new QualityReportFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, qualityReportFragment).commit();
            }
            else {
                Intent i = new Intent(this, QualityReportCreateActivity.class);
                startActivity(i);
            }

        } else if (id == R.id.nav_graph) {
            e.putInt("fragment", 3);
            fab.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(this, ProfileEditActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            e.remove("cookie");
            e.remove("fragment");
            e.remove("name");
            e.remove("email");
            e.commit();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        e.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        new SourceReportTask(this,this).execute(sp.getString("cookie", null));
    }

    @Override
    public void onGotData(Response<SourceReport[]> s) {

        if (s.getSuccess() == 1){
            MarkerAdapter m = new MarkerAdapter(s.getData(), this);
            googleMap.setInfoWindowAdapter(m);
            for (SourceReport report : s.getData()){
                googleMap.addMarker(report.getMarker());
            }
        }
        else {
            Log.d("debug", "failed to get source reports...");
        }
    }
}
