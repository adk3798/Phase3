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

public class ParentRegistrationActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    EditText etName;
    EditText etPhone;
    Button bRegister;
    Button bMainPage;
    TextView tvMessage;
    private static final String TAG = "RegisterParent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        bRegister = (Button) findViewById(R.id.bRegister);
        bMainPage = (Button) findViewById(R.id.bMainPage);
        tvMessage = (TextView) findViewById(R.id.tvMessage);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "xxxxxxxxxxxxxxxxxxxxxx\n");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            String error_msg = jsonResponse.getString("error");
                            if(success.equals("true")) {
                                tvMessage.setText("Successfully Registered");
                                String pname = jsonResponse.getString("name");
                                int pid = Integer.parseInt(jsonResponse.getString("pid"));
                                Intent pRegisterIntent = new Intent(ParentRegistrationActivity.this, ParentActivity.class);
                                pRegisterIntent.putExtra("name", pname);
                                pRegisterIntent.putExtra("pid", pid);
                                ParentRegistrationActivity.this.startActivity(pRegisterIntent);
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
                ParentRegistrationRequest pRR =
                        new ParentRegistrationRequest(email, password, name, phone, getString(R.string.BASE_SERVER_URL) + "registerParent.php", listener);
                RequestQueue queue = Volley.newRequestQueue(ParentRegistrationActivity.this);
                queue.add(pRR);

            }

        });

        bMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pMainPageIntent = new Intent(ParentRegistrationActivity.this, LandingActivity.class);
                ParentRegistrationActivity.this.startActivity(pMainPageIntent);

            }

        });


    }
}
