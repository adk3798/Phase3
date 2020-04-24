package com.example.android.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {

    Button bLogout;
    Button bEditInfo;
    Button bMeetings;
    Button bMentorInfo;
    Button bStudyMaterials;
    TextView tvHeader;
    private static final String TAG = "StudentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        bLogout = (Button) findViewById(R.id.bLogout);
        bEditInfo = (Button) findViewById(R.id.bEditInfo);
        bMeetings = (Button) findViewById(R.id.bMeetings);
        bMentorInfo = (Button) findViewById(R.id.bMentorInfo);
        bStudyMaterials = (Button) findViewById(R.id.bStudyMaterials);
        tvHeader = (TextView) findViewById(R.id.tvMessage);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int sid = intent.getIntExtra("sid", -1);

        tvHeader.setText("Welcome, " + name);

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sMainPageIntent = new Intent(StudentActivity.this, LandingActivity.class);
                StudentActivity.this.startActivity(sMainPageIntent);
            }

        });

        bEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sEditIntent = new Intent(StudentActivity.this, StudentEditActivity.class);
                sEditIntent.putExtra("name", name);
                sEditIntent.putExtra("sid", sid);
                StudentActivity.this.startActivity(sEditIntent);
            }

        });

        bMentorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sMentorInfoIntent = new Intent(StudentActivity.this, StudentMentorInfoActivity.class);
                sMentorInfoIntent.putExtra("name", name);
                sMentorInfoIntent.putExtra("sid", sid);
                StudentActivity.this.startActivity(sMentorInfoIntent);
            }

        });

        bStudyMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sStudyMaterialsIntent = new Intent(StudentActivity.this, StudentStudyMaterialsActivity.class);
                sStudyMaterialsIntent.putExtra("name", name);
                sStudyMaterialsIntent.putExtra("sid", sid);
                StudentActivity.this.startActivity(sStudyMaterialsIntent);
            }

        });

        bMeetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sMeetingIntent = new Intent(StudentActivity.this, StudentJoinMeetingActivity.class);
                sMeetingIntent.putExtra("name", name);
                sMeetingIntent.putExtra("sid", sid);
                StudentActivity.this.startActivity(sMeetingIntent);
            }

        });

    }
}
