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

public class StudentStudyMaterialsActivity extends AppCompatActivity {

        Button bMainPage;
        TextView tvHeader;
        LinearLayout lStudyMaterials;
        private static final String TAG = "StudyMaterials";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_study_materials);

            tvHeader = (TextView) findViewById(R.id.tvHeader);
            bMainPage = (Button) findViewById(R.id.bMainPage);
            lStudyMaterials = (LinearLayout) findViewById(R.id.lStudyMaterials);

            TextView infoTxt = new TextView(StudentStudyMaterialsActivity.this);

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
                            TextView meetingTxt = new TextView(StudentStudyMaterialsActivity.this);
                            String meeting = mname + " " + mdate + " " + mday + " " + mstart + " to " + mend + "\n";
                            meetingTxt.append(meeting);
                            meetingTxt.append("  Study Materials:\n");
                            int j = 0;
                            while(jsonResponse.has(Integer.toString(i) + "x" + Integer.toString(j) + "title")) {
                                Log.d(TAG, "fffffffffffffffffffff\n");
                                String stitle = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "title");
                                String sauthor = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "author");
                                String stype = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "type");
                                String surl = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "url");
                                String sdate = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "assigned_date");
                                String snotes = jsonResponse.getString(Integer.toString(i) + "x" +  Integer.toString(j) + "notes");
                                String title = "    title: " + stitle + "\n";
                                String author = "    author: " + sauthor + "\n";
                                String type = "    type: " + stype + "\n";
                                String url = "    URL: " + surl + "\n";
                                String date = "    date assigned: " + sdate + "\n";
                                String notes = "    notes: " + snotes + "\n";
                                meetingTxt.append(title + author + type + url + date + notes);
                                j++;
                            }
                            if(j == 0) {
                                meetingTxt.append("    None " + "\n");
                            }
                            lStudyMaterials.addView(meetingTxt);
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
            GetStudentStudyMaterialsRequest sSMR = new GetStudentStudyMaterialsRequest(sid,
                    getString(R.string.BASE_SERVER_URL) + "GetStudentStudyMaterials.php", listener);
            RequestQueue queue = Volley.newRequestQueue(StudentStudyMaterialsActivity.this);
            queue.add(sSMR);

            bMainPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sReturn = new Intent(StudentStudyMaterialsActivity.this, StudentActivity.class);
                    sReturn.putExtra("name", name);
                    sReturn.putExtra("sid", sid);
                    StudentStudyMaterialsActivity.this.startActivity(sReturn);
                }

            });
    }
}
