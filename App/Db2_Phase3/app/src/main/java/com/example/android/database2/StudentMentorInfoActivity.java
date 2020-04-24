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

public class StudentMentorInfoActivity extends AppCompatActivity {

    Button bMainPage;
    TextView tvHeader;
    LinearLayout lInfo;
    private static final String TAG = "StudentMentorInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_info);

        tvHeader = (TextView) findViewById(R.id.tvHeader);
        bMainPage = (Button) findViewById(R.id.bMainPage);
        lInfo = (LinearLayout) findViewById(R.id.lInfo);

        TextView infoTxt = new TextView(StudentMentorInfoActivity.this);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int sid = intent.getIntExtra("sid", -1);

        tvHeader.append(" " + name);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "xxxxxxxxxxxxxxxxxxxxxx\n");
                Log.d(TAG, response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int i = 0;
                    while(jsonResponse.has(Integer.toString(i) + "mid")) {
                        String mdate = jsonResponse.getString(Integer.toString(i) + "mdate");
                        String mname = jsonResponse.getString(Integer.toString(i) + "mname");
                        String mday = jsonResponse.getString(Integer.toString(i) + "mday");
                        String mstart = jsonResponse.getString(Integer.toString(i) + "mstart");
                        String mend = jsonResponse.getString(Integer.toString(i) + "mend");
                        int mid = Integer.parseInt(jsonResponse.getString(Integer.toString(i) + "mid"));
                        TextView meetingTxt = new TextView(StudentMentorInfoActivity.this);
                        String meeting = mname + " " + mdate + " " + mday + " " + mstart + " to " + mend + "\n";
                        meetingTxt.append(meeting);
                        meetingTxt.append("  Mentors:\n");
                        int j = 0;
                        while(jsonResponse.has(Integer.toString(i) + "x" + Integer.toString(j) + "email")) {
                            String semail = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "email");
                            String sname = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "name");
                            String mentor = "    " + sname + " " + semail + "\n";
                            meetingTxt.append(mentor);
                            j++;
                        }
                        meetingTxt.append("  Mentees:\n");
                        j = 0;
                        while(jsonResponse.has(Integer.toString(i) + "z" + Integer.toString(j) + "email")) {
                            String semail = jsonResponse.getString(Integer.toString(i) + "z" +  Integer.toString(j) + "email");
                            String sname = jsonResponse.getString(Integer.toString(i) + "z" +  Integer.toString(j) + "name");
                            String mentee = "    " + sname + " " + semail + "\n";
                            meetingTxt.append(mentee);
                            j++;
                        }
                        lInfo.addView(meetingTxt);
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
        GetMentorMeetingsAttendeesRequest sMMAR = new GetMentorMeetingsAttendeesRequest(sid,
                getString(R.string.BASE_SERVER_URL) + "GetMentorMeetingsAttendees.php", listener);
        RequestQueue queue = Volley.newRequestQueue(StudentMentorInfoActivity.this);
        queue.add(sMMAR);

        bMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sReturn = new Intent(StudentMentorInfoActivity.this, StudentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("sid", sid);
                StudentMentorInfoActivity.this.startActivity(sReturn);
            }

        });
    }
}
