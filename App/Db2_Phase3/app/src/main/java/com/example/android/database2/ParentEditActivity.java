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

public class ParentEditActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    EditText etName;
    EditText etPhone;
    Button bRegister;
    Button bMainPage;
    TextView tvMessage;
    CheckBox cbEmail;
    CheckBox cbPassword;
    CheckBox cbName;
    CheckBox cbPhone;
    private static final String TAG = "EditParent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_edit);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int pid = intent.getIntExtra("pid", -1);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        bRegister = (Button) findViewById(R.id.bRegister);
        bMainPage = (Button) findViewById(R.id.bMainPage);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        cbEmail = (CheckBox) findViewById(R.id.cbEmail);
        cbPassword = (CheckBox) findViewById(R.id.cbPassword);
        cbName = (CheckBox) findViewById(R.id.cbName);
        cbPhone = (CheckBox) findViewById(R.id.cbPhone);

        bMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pReturn = new Intent(ParentEditActivity.this, ParentActivity.class);
                pReturn.putExtra("name", name);
                pReturn.putExtra("pid", pid);
                ParentEditActivity.this.startActivity(pReturn);
            }

        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                final String new_name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                boolean changeEmail = ((CheckBox) cbEmail).isChecked();
                boolean changePassword = ((CheckBox) cbPassword).isChecked();
                final boolean changeName = ((CheckBox) cbName).isChecked();
                boolean changePhone = ((CheckBox) cbPhone).isChecked();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "xxxxxxxxxxxxxxxxxxxxxx\n");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            String error_msg = jsonResponse.getString("error");
                            /////////////////////////////
                            ///// Have it go back to parent activity with new name
                            ////////////////////////////
                            if(success.equals("true")) {
                                tvMessage.setText("Successfully Edited Info");
                                Intent pEditSuccessIntent = new Intent(ParentEditActivity.this, ParentActivity.class);
                                if(changeName) {
                                    pEditSuccessIntent.putExtra("name", new_name);
                                } else {
                                    pEditSuccessIntent.putExtra("name", name);
                                }
                                pEditSuccessIntent.putExtra("pid", pid);
                                ParentEditActivity.this.startActivity(pEditSuccessIntent);
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
                ParentEditRequest pER =
                        new ParentEditRequest(email, password, new_name, phone,  changeEmail, changePassword,
                                changeName, changePhone, pid, getString(R.string.BASE_SERVER_URL) + "editParent.php", listener);
                RequestQueue queue = Volley.newRequestQueue(ParentEditActivity.this);
                queue.add(pER);

            }

        });
    }
}
