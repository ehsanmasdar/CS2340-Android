package com.ehsandev.cs2340.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ehsandev.cs2340.R;
import com.ehsandev.cs2340.model.Response;
import com.ehsandev.cs2340.model.User;
import com.ehsandev.cs2340.task.RegisterTask;


public class RegisterActivity extends AppCompatActivity implements RegisterTask.AsyncTaskCompleteListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Spinner s = (Spinner) findViewById(R.id.spinner_access_level);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        setTitle("Register");
    }

    public void onRegisterRealClick (View v){
        Spinner s = (Spinner) findViewById(R.id.spinner_access_level);
        EditText username = (EditText) findViewById(R.id.edittext_login_username);
        EditText password = (EditText) findViewById(R.id.edittext_login_password);
        EditText passwordConfirm = (EditText) findViewById(R.id.edittext_login_password_confirm);
        if (password.getText().toString().equals(passwordConfirm.getText().toString())){
            new RegisterTask(this,this).execute(new User(username.getText().toString(), password.getText().toString(), (String) s.getSelectedItem().toString().toLowerCase()));
        }
    }

    @Override
    public void onLoginDone(Response<String> r) {
        if (r.getSuccess() == 1) {
            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor e = p.edit();
            e.putString("cookie", r.getData());
            Log.d("debug", "setting cookie to " + r.getData());
            e.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
