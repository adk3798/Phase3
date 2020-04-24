package com.example.android.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ParentActivity extends AppCompatActivity {

    Button bLogout;
    Button bEditInfo;
    TextView tvHeader;
    LinearLayout lStudentEdit;
    private static final String TAG = "ParentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        bLogout = (Button) findViewById(R.id.bLogout);
        bEditInfo = (Button) findViewById(R.id.bEditInfo);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        lStudentEdit = (LinearLayout) findViewById(R.id.lStudentEdit);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int pid = intent.getIntExtra("pid", -1);

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
                        TextView studentName = new TextView(ParentActivity.this);
                        studentName.setText(sname + ":");
                        ParentEditStudentButton bPES = new ParentEditStudentButton(ParentActivity.this, "Edit Student's Info", sid, sname, pid, name);
                        ParentJoinMeetingButton bPJM = new ParentJoinMeetingButton(ParentActivity.this, "Add/Remove Student From Meetings", sid, sname, pid, name);
                        ParentMentorInfoButton bPMM = new ParentMentorInfoButton(ParentActivity.this, "View Mentor Meetings Attendees", sid, sname, pid, name);
                        ParentStudyMaterialsButton bPSM = new ParentStudyMaterialsButton(ParentActivity.this, "View Student's Study Materials", sid, sname, pid, name);
                        lStudentEdit.addView(studentName);
                        lStudentEdit.addView(bPES.getButton());
                        lStudentEdit.addView(bPJM.getButton());
                        lStudentEdit.addView(bPMM.getButton());
                        lStudentEdit.addView(bPSM.getButton());
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
        GetParentsStudentsRequest pPSR = new GetParentsStudentsRequest(pid, getString(R.string.BASE_SERVER_URL) + "GetParentsStudents.php", listener);
        RequestQueue queue = Volley.newRequestQueue(ParentActivity.this);
        queue.add(pPSR);

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pMainPageIntent = new Intent(ParentActivity.this, LandingActivity.class);
                ParentActivity.this.startActivity(pMainPageIntent);
            }
        });

        bEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pEditIntent = new Intent(ParentActivity.this, ParentEditActivity.class);
                pEditIntent.putExtra("name", name);
                pEditIntent.putExtra("pid", pid);
                ParentActivity.this.startActivity(pEditIntent);
            }

        });
    }
}
