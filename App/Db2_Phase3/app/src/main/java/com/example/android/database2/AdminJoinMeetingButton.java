package com.example.android.database2;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AdminJoinMeetingButton {
    private Button bt;
    private int sid;
    private String sname;
    private int aid;
    private String aname;
    public AdminJoinMeetingButton(final Context context, String text, int student_id, String student_name, int admin_id, String admin_name) {
        //here set the properties
        sname = student_name;
        sid = student_id;
        aid = admin_id;
        aname = admin_name;
        bt = new Button(context);
        bt.setText(text);
        bt.setId(student_id);
        bt.setTag(student_id);
        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handle the button click, unique id , you can use to differentiate
                Intent psEditIntent = new Intent(context, AdminJoinMeetingActivity.class);
                psEditIntent.putExtra("name", aname);
                psEditIntent.putExtra("aid", aid);
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
