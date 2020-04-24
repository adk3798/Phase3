package com.example.android.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button bLogin;
    Button bMainPage;
    TextView tvMessage;
    private static final String TAG = "LoginAdmin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bMainPage = (Button) findViewById(R.id.bMainPage);
        tvMessage = (TextView) findViewById(R.id.tvMessage);

        bMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pMainPageIntent = new Intent(AdminLoginActivity.this, LandingActivity.class);
                AdminLoginActivity.this.startActivity(pMainPageIntent);
            }

        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "xxxxxxxxxxxxxxxxxxxxxx\n");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            String error_msg = jsonResponse.getString("error");
                            if(success.equals("true")) {
                                tvMessage.setText("Successfully Logged In");
                                String name = jsonResponse.getString("name");
                                int aid = jsonResponse.getInt("aid");
                                Intent pLoginIntent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                                pLoginIntent.putExtra("name", name);
                                pLoginIntent.putExtra("aid", aid);
                                AdminLoginActivity.this.startActivity(pLoginIntent);
                            }
                            else if(success.equals("false")) {
                                tvMessage.setText(error_msg);
                            }
                            else{
                                tvMessage.setText("Unknown Success Status Received!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, response);
                        }
                        Log.d(TAG, "yyyyyyyyyyyyyyyyyyyyyyy\n");
                    }
                };
                Log.d(TAG, "bbbbbbbbbbbbbbbbbbb\n");
                // Must have url passed in as arg to make use of string resources without using contexts
                AdminLoginRequest aLR =
                        new AdminLoginRequest(email, password, getString(R.string.BASE_SERVER_URL) + "loginAdmin.php", listener);
                RequestQueue queue = Volley.newRequestQueue(AdminLoginActivity.this);
                queue.add(aLR);

            }

        });
    }
}
