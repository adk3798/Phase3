package com.example.android.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ParentEditStudentActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    EditText etName;
    EditText etPhone;
    Button bRegister;
    Button bMainPage;
    TextView tvMessage;
    TextView tvSName;
    Spinner spGrade;
    CheckBox cbEmail;
    CheckBox cbPassword;
    CheckBox cbName;
    CheckBox cbPhone;
    CheckBox cbGrade;
    private static final String TAG = "EditStudentWithParent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_edit_student);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int pid = intent.getIntExtra("pid", -1);
        final String student_name = intent.getStringExtra("sname");
        final int sid = intent.getIntExtra("sid", -1);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        bRegister = (Button) findViewById(R.id.bRegister);
        bMainPage = (Button) findViewById(R.id.bMainPage);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvSName = (TextView) findViewById(R.id.tvSName);
        spGrade = (Spinner) findViewById(R.id.spGrade);
        cbEmail = (CheckBox) findViewById(R.id.cbEmail);
        cbPassword = (CheckBox) findViewById(R.id.cbPassword);
        cbName = (CheckBox) findViewById(R.id.cbName);
        cbPhone = (CheckBox) findViewById(R.id.cbPhone);
        cbGrade = (CheckBox) findViewById(R.id.cbGrade);

        tvSName.append(student_name);

        bMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sReturn = new Intent(ParentEditStudentActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                ParentEditStudentActivity.this.startActivity(sReturn);
            }

        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                final String new_name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String grade = String.valueOf(spGrade.getSelectedItem());
                boolean changeEmail = ((CheckBox) cbEmail).isChecked();
                boolean changePassword = ((CheckBox) cbPassword).isChecked();
                final boolean changeName = ((CheckBox) cbName).isChecked();
                boolean changePhone = ((CheckBox) cbPhone).isChecked();
                boolean changeGrade = ((CheckBox) cbGrade).isChecked();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "xxxxxxxxxxxxxxxxxxxxxx\n");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            String error_msg = jsonResponse.getString("error");
                            if(success.equals("true")) {
                                tvMessage.setText("Successfully Edited Info");
                                Intent psEditSuccessIntent = new Intent(ParentEditStudentActivity.this, ParentActivity.class);
                                psEditSuccessIntent.putExtra("pid", pid);
                                psEditSuccessIntent.putExtra("name", name);
                                ParentEditStudentActivity.this.startActivity(psEditSuccessIntent);
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
                StudentEditRequest psER =
                        new StudentEditRequest(email, password, new_name, phone, grade, changeEmail, changePassword,
                                changeName, changePhone, changeGrade, sid, getString(R.string.BASE_SERVER_URL) + "editStudent.php", listener);
                RequestQueue queue = Volley.newRequestQueue(ParentEditStudentActivity.this);
                queue.add(psER);

            }

        });
    }
}
