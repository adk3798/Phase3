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

import java.util.ArrayList;

public class ParentJoinMeetingActivity extends AppCompatActivity {
    TextView tvHeader;
    Button bJoinMentor;
    Button bLeaveMentor;
    Button bJoinMentee;
    Button bLeaveMentee;
    Button bLeaveMentorAll;
    Button bLeaveMenteeAll;
    Button bMainPage;
    LinearLayout lMentorJoin;
    LinearLayout lMentorLeave;
    LinearLayout lMenteeJoin;
    LinearLayout lMenteeLeave;
    private static final String TAG = "ParentMeetings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting);

        tvHeader = (TextView) findViewById(R.id.tvHeader);
        bJoinMentor = (Button) findViewById(R.id.bJoinMentor);
        bLeaveMentor = (Button) findViewById(R.id.bLeaveMentor);
        bLeaveMentorAll = (Button) findViewById(R.id.bLeaveMentorAll);
        bJoinMentee = (Button) findViewById(R.id.bJoinMentee);
        bLeaveMentee = (Button) findViewById(R.id.bLeaveMentee);
        bLeaveMenteeAll = (Button) findViewById(R.id.bLeaveMenteeAll);
        bMainPage = (Button) findViewById(R.id.bMainPage);
        lMentorJoin = (LinearLayout) findViewById(R.id.lMentorJoin);
        lMentorLeave = (LinearLayout) findViewById(R.id.lMentorLeave);
        lMenteeJoin = (LinearLayout) findViewById(R.id.lMenteeJoin);
        lMenteeLeave = (LinearLayout) findViewById(R.id.lMenteeLeave);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int pid = intent.getIntExtra("pid", -1);
        final String student_name = intent.getStringExtra("sname");
        final int sid = intent.getIntExtra("sid", -1);

        tvHeader.setText("Add/Remove " + student_name);

        final ArrayList<MeetingCheckbox> mentorjoin = new ArrayList<MeetingCheckbox>();

        Response.Listener<String> mentorjoinlistener = new Response.Listener<String>() {
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
                        TextView meetingTxt = new TextView(ParentJoinMeetingActivity.this);
                        meetingTxt.setText(mname + " " + mdate + " " + mday + " " + mstart + " to " + mend);
                        MeetingCheckbox mch = new MeetingCheckbox(ParentJoinMeetingActivity.this, mid);
                        mentorjoin.add(mch);
                        LinearLayout lrow = new LinearLayout(ParentJoinMeetingActivity.this);
                        lrow.addView(mch.getCheckbox());
                        lrow.addView(meetingTxt);
                        lMentorJoin.addView(lrow);
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
        GetStudentsPossibleMentorMeetingsRequest sMMR = new GetStudentsPossibleMentorMeetingsRequest(sid,
                getString(R.string.BASE_SERVER_URL) + "GetStudentsPossibleMentorMeetings.php", mentorjoinlistener);
        RequestQueue mentorjoinqueue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
        mentorjoinqueue.add(sMMR);

        bMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sReturn = new Intent(ParentJoinMeetingActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                ParentJoinMeetingActivity.this.startActivity(sReturn);
            }

        });

        bJoinMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < mentorjoin.size(); i++) {
                    if(mentorjoin.get(i).getCheckbox().isChecked()) {
                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) { }
                        };
                        JoinMeetingRequest jmR = new JoinMeetingRequest(sid, mentorjoin.get(i).getMid(), "mentor",
                                getString(R.string.BASE_SERVER_URL) + "JoinMeeting.php", listener);
                        RequestQueue queue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
                        queue.add(jmR);
                    }
                }
                Intent sReturn = new Intent(ParentJoinMeetingActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                sReturn.putExtra("sname", student_name);
                sReturn.putExtra("sid", sid);
                ParentJoinMeetingActivity.this.startActivity(sReturn);
            }
        });

        final ArrayList<MeetingCheckbox> mentorleave = new ArrayList<MeetingCheckbox>();

        Response.Listener<String> mentorleavelistener = new Response.Listener<String>() {
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
                        TextView meetingTxt = new TextView(ParentJoinMeetingActivity.this);
                        meetingTxt.setText(mname + " " + mdate + " " + mday + " " + mstart + " to " + mend);
                        MeetingCheckbox mch = new MeetingCheckbox(ParentJoinMeetingActivity.this, mid);
                        mentorleave.add(mch);
                        LinearLayout lrow = new LinearLayout(ParentJoinMeetingActivity.this);
                        lrow.addView(mch.getCheckbox());
                        lrow.addView(meetingTxt);
                        lMentorLeave.addView(lrow);
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
        GetStudentsMentorMeetingsRequest sMMR2 = new GetStudentsMentorMeetingsRequest(sid,
                getString(R.string.BASE_SERVER_URL) + "GetStudentsMentorMeetings.php", mentorleavelistener);
        RequestQueue mentorleavequeue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
        mentorleavequeue.add(sMMR2);

        bLeaveMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < mentorleave.size(); i++) {
                    if(mentorleave.get(i).getCheckbox().isChecked()) {
                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) { }
                        };
                        LeaveMeetingRequest lmR = new LeaveMeetingRequest(sid, mentorleave.get(i).getMid(), "mentor",
                                getString(R.string.BASE_SERVER_URL) + "LeaveMeeting.php", listener);
                        RequestQueue queue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
                        queue.add(lmR);
                    }
                }
                Intent sReturn = new Intent(ParentJoinMeetingActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                sReturn.putExtra("sname", student_name);
                sReturn.putExtra("sid", sid);
                ParentJoinMeetingActivity.this.startActivity(sReturn);
            }
        });

        bLeaveMentorAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < mentorleave.size(); i++) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { }
                    };
                    LeaveMeetingRequest lmR = new LeaveMeetingRequest(sid, mentorleave.get(i).getMid(), "mentor",
                            getString(R.string.BASE_SERVER_URL) + "LeaveMeeting.php", listener);
                    RequestQueue queue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
                    queue.add(lmR);

                }
                Intent sReturn = new Intent(ParentJoinMeetingActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                sReturn.putExtra("sname", student_name);
                sReturn.putExtra("sid", sid);
                ParentJoinMeetingActivity.this.startActivity(sReturn);
            }
        });

        final ArrayList<MeetingCheckbox> menteejoin = new ArrayList<MeetingCheckbox>();

        Response.Listener<String> menteejoinlistener = new Response.Listener<String>() {
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
                        TextView meetingTxt = new TextView(ParentJoinMeetingActivity.this);
                        meetingTxt.setText(mname + " " + mdate + " " + mday + " " + mstart + " to " + mend);
                        MeetingCheckbox mch = new MeetingCheckbox(ParentJoinMeetingActivity.this, mid);
                        menteejoin.add(mch);
                        LinearLayout lrow = new LinearLayout(ParentJoinMeetingActivity.this);
                        lrow.addView(mch.getCheckbox());
                        lrow.addView(meetingTxt);
                        lMenteeJoin.addView(lrow);
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
        GetStudentsPossibleMenteeMeetingsRequest sMMR3 = new GetStudentsPossibleMenteeMeetingsRequest(sid,
                getString(R.string.BASE_SERVER_URL) + "GetStudentsPossibleMenteeMeetings.php", menteejoinlistener);
        RequestQueue menteejoinqueue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
        menteejoinqueue.add(sMMR3);


        bJoinMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < menteejoin.size(); i++) {
                    if(menteejoin.get(i).getCheckbox().isChecked()) {
                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) { }
                        };
                        JoinMeetingRequest jmR = new JoinMeetingRequest(sid, menteejoin.get(i).getMid(), "mentee",
                                getString(R.string.BASE_SERVER_URL) + "JoinMeeting.php", listener);
                        RequestQueue queue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
                        queue.add(jmR);
                    }
                }
                Intent sReturn = new Intent(ParentJoinMeetingActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                sReturn.putExtra("sname", student_name);
                sReturn.putExtra("sid", sid);
                ParentJoinMeetingActivity.this.startActivity(sReturn);
            }
        });

        final ArrayList<MeetingCheckbox> menteeleave = new ArrayList<MeetingCheckbox>();

        Response.Listener<String> menteeleavelistener = new Response.Listener<String>() {
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
                        TextView meetingTxt = new TextView(ParentJoinMeetingActivity.this);
                        meetingTxt.setText(mname + " " + mdate + " " + mday + " " + mstart + " to " + mend);
                        MeetingCheckbox mch = new MeetingCheckbox(ParentJoinMeetingActivity.this, mid);
                        menteeleave.add(mch);
                        LinearLayout lrow = new LinearLayout(ParentJoinMeetingActivity.this);
                        lrow.addView(mch.getCheckbox());
                        lrow.addView(meetingTxt);
                        lMenteeLeave.addView(lrow);
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
        GetStudentsMenteeMeetingsRequest sMMR4 = new GetStudentsMenteeMeetingsRequest(sid,
                getString(R.string.BASE_SERVER_URL) + "GetStudentsMenteeMeetings.php", menteeleavelistener);
        RequestQueue queue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
        queue.add(sMMR4);

        bLeaveMentee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < menteeleave.size(); i++) {
                    if(menteeleave.get(i).getCheckbox().isChecked()) {
                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) { }
                        };
                        LeaveMeetingRequest lmR = new LeaveMeetingRequest(sid, menteeleave.get(i).getMid(), "mentee",
                                getString(R.string.BASE_SERVER_URL) + "LeaveMeeting.php", listener);
                        RequestQueue queue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
                        queue.add(lmR);
                    }
                }
                Intent sReturn = new Intent(ParentJoinMeetingActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                sReturn.putExtra("sname", student_name);
                sReturn.putExtra("sid", sid);
                ParentJoinMeetingActivity.this.startActivity(sReturn);
            }
        });

        bLeaveMenteeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < menteeleave.size(); i++) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) { }
                    };
                    LeaveMeetingRequest lmR = new LeaveMeetingRequest(sid, menteeleave.get(i).getMid(), "mentee",
                            getString(R.string.BASE_SERVER_URL) + "LeaveMeeting.php", listener);
                    RequestQueue queue = Volley.newRequestQueue(ParentJoinMeetingActivity.this);
                    queue.add(lmR);
                }
                Intent sReturn = new Intent(ParentJoinMeetingActivity.this, ParentActivity.class);
                sReturn.putExtra("name", name);
                sReturn.putExtra("pid", pid);
                sReturn.putExtra("sname", student_name);
                sReturn.putExtra("sid", sid);
                ParentJoinMeetingActivity.this.startActivity(sReturn);
            }
        });
    }
}
