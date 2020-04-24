package com.example.android.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminActivity extends AppCompatActivity {

    Button bLogout;
    TextView tvHeader;
    LinearLayout lStudentEdit;
    private static final String TAG = "ParentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bLogout = (Button) findViewById(R.id.bLogout);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        lStudentEdit = (LinearLayout) findViewById(R.id.lStudentEdit);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int aid = intent.getIntExtra("aid", -1);

        tvHeader.setText("Welcome, " + name);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "xxxxxxxxxxxxxxxxxxxxxx\n");
                Log.d(TAG, response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int i = 0;
                    while(jsonResponse.has(Integer.toString(i) + "sid")) {
                        String sname = jsonResponse.getString(Integer.toString(i) + "name");
                        int sid = Integer.parseInt(jsonResponse.getString(Integer.toString(i) + "sid"));
                        int grade = Integer.parseInt(jsonResponse.getString(Integer.toString(i) + "grade"));
                        TextView studentName = new TextView(AdminActivity.this);
                        studentName.setText(sname + " (grade " + grade +  "):");
                        AdminJoinMeetingButton bAJM = new AdminJoinMeetingButton(AdminActivity.this, "Add/Remove Student From Meetings", sid, sname, aid, name);
                        lStudentEdit.addView(studentName);
                        lStudentEdit.addView(bAJM.getButton());
                        i++;
                    }
                } catch (JSONException e) {
                    Log.d(TAG, response);
                    e.printStackTrace();
                }
                Log.d(TAG, "yyyyyyyyyyyyyyyyyyyyyyy\n");
            }
        };
        // Must have url passed in as arg to make use of string resources without using contexts
        GetStudentsRequest aSR = new GetStudentsRequest(getString(R.string.BASE_SERVER_URL) + "GetStudents.php", listener);
        RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
        queue.add(aSR);

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pMainPageIntent = new Intent(AdminActivity.this, LandingActivity.class);
                AdminActivity.this.startActivity(pMainPageIntent);
            }
        });
    }
}
