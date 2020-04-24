package com.example.android.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentRegistrationActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPEmail;
    EditText etPassword;
    EditText etName;
    EditText etPhone;
    Button bRegister;
    Button bMainPage;
    TextView tvMessage;
    Spinner spGrade;
    private static final String TAG = "RegisterStudent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPEmail = (EditText) findViewById(R.id.etPEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        bRegister = (Button) findViewById(R.id.bRegister);
        bMainPage = (Button) findViewById(R.id.bMainPage);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        spGrade = (Spinner) findViewById(R.id.spGrade);

        bMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pMainPageIntent = new Intent(StudentRegistrationActivity.this, LandingActivity.class);
                StudentRegistrationActivity.this.startActivity(pMainPageIntent);
            }

        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String parentEmail = etPEmail.getText().toString();
                String grade = String.valueOf(spGrade.getSelectedItem());

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
                                String sname = jsonResponse.getString("name");
                                int sid = Integer.parseInt(jsonResponse.getString("sid"));
                                Intent sRegisterIntent = new Intent(StudentRegistrationActivity.this, StudentActivity.class);
                                sRegisterIntent.putExtra("name", sname);
                                sRegisterIntent.putExtra("sid", sid);
                                StudentRegistrationActivity.this.startActivity(sRegisterIntent);
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
                StudentRegistrationRequest sRR =
                        new StudentRegistrationRequest(email, parentEmail, password, name, phone, grade, getString(R.string.BASE_SERVER_URL) + "registerStudent.php", listener);
                RequestQueue queue = Volley.newRequestQueue(StudentRegistrationActivity.this);
                queue.add(sRR);

            }

        });
    }
}
