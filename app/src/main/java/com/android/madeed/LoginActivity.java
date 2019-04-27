package com.android.madeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void authenticate(View view)
    {

        EditText username = (EditText) findViewById(R.id.Username);
        EditText password = (EditText) findViewById(R.id.password);

        String username_value = username.getText().toString();
        String password_value = password.getText().toString();


        if( (username_value.equals("Educationteam")) && (password_value.equals("password"))) {
            Toast.makeText(this, "true",  Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
        } else {
            Toast.makeText(this, "Password and username don't match",  Toast.LENGTH_SHORT).show();

        }




    }



}
