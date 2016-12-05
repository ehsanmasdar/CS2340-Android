package com.ehsandev.cs2340.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.model.Profile;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.task.ProfileSubmitTask;
import com.ehsandev.cs2340.task.ProfileTask;

public class ProfileEditActivity extends AppCompatActivity implements ProfileTask.AsyncTaskCompleteListener, ProfileSubmitTask.AsyncTaskCompleteListener {

    private Profile submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Define Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Configure Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        new ProfileTask(this,this).execute(sp.getString("cookie", null));
    }

    @Override
    public void onGotData(Response<Profile> s) {
        Profile p = s.getData();
        EditText name = (EditText) findViewById(R.id.nameInput);
        EditText email = (EditText) findViewById(R.id.emailInput);
        EditText address = (EditText) findViewById(R.id.addressInput);
        if (p.getName() != null)
            name.setText(p.getName());
        if (p.getEmail() != null)
            email.setText(p.getEmail());
        if (p.getAddress() != null)
            address.setText(p.getAddress());
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
            EditText name = (EditText) findViewById(R.id.nameInput);
            EditText email = (EditText) findViewById(R.id.emailInput);
            EditText address = (EditText) findViewById(R.id.addressInput);
            submit = new Profile(name.getText().toString(), address.getText().toString(), email.getText().toString());
            new ProfileSubmitTask(this, this, sp.getString("cookie", null)).execute(
                    submit);
            return true;
        }
        return true;
    }

    @Override
    public void onDone(Response<String> s) {
        if (s.getSuccess() == 1){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor  e = sp.edit();
            e.putString("name", submit.getName());
            e.putString("email", submit.getEmail());
            e.apply();
            finish();
        }
    }
}
