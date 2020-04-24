package com.example.android.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LandingActivity extends AppCompatActivity {

    Button bPRegister;
    Button bPLogin;
    Button bSRegister;
    Button bSLogin;
    Button bALogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        bPRegister = (Button) findViewById(R.id.bPRegister);
        bPLogin = (Button) findViewById(R.id.bPLogin);
        bSRegister = (Button) findViewById(R.id.bSRegister);
        bSLogin = (Button) findViewById(R.id.bSLogin);
        bALogin = (Button) findViewById(R.id.bALogin);

        bPRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Parent Reg button", "parent register button clicked\n");
                Intent pRegIntent = new Intent(LandingActivity.this, ParentRegistrationActivity.class);
                LandingActivity.this.startActivity(pRegIntent);
            }
        });

        bPLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Parent Login button", "parent login button clicked\n");
                Intent pLoginIntent = new Intent(LandingActivity.this, ParentLoginActivity.class);
                LandingActivity.this.startActivity(pLoginIntent);
            }
        });

        bSRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Student Reg button", "student register button clicked\n");
                Intent sRegIntent = new Intent(LandingActivity.this, StudentRegistrationActivity.class);
                LandingActivity.this.startActivity(sRegIntent);
            }
        });

        bSLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Student Login button", "student login button clicked\n");
                Intent sLoginIntent = new Intent(LandingActivity.this, StudentLoginActivity.class);
                LandingActivity.this.startActivity(sLoginIntent);
            }
        });

        bALogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Admin Login button", "admin login button clicked\n");
                Intent aLoginIntent = new Intent(LandingActivity.this, AdminLoginActivity.class);
                LandingActivity.this.startActivity(aLoginIntent);
            }
        });
    }
}
