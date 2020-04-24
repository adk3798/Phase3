package com.example.android.database2;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ParentJoinMeetingButton {
    private Button bt;
    private int sid;
    private String sname;
    private int pid;
    private String pname;
    public ParentJoinMeetingButton(final Context context, String text, int student_id, String student_name, int parent_id, String parent_name) {
        //here set the properties
        sname = student_name;
        sid = student_id;
        pid = parent_id;
        pname = parent_name;
        bt = new Button(context);
        bt.setText(text);
        bt.setId(student_id);
        bt.setTag(student_id);
        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handle the button click, unique id , you can use to differentiate
                Intent psEditIntent = new Intent(context, ParentJoinMeetingActivity.class);
                psEditIntent.putExtra("name", pname);
                psEditIntent.putExtra("pid", pid);
                psEditIntent.putExtra("sname", sname);
                psEditIntent.putExtra("sid", sid);
                context.startActivity(psEditIntent);

            }

        });
    }
    public Button getButton() {
        return bt;
    }
    public int getId() {
        return bt.getId();
    }
    public int getTag() {
        return (int) bt.getTag();
    }
}
