package com.example.android.database2;

import android.content.Context;
import android.widget.CheckBox;

public class MeetingCheckbox {

    private int mid;
    CheckBox ch;

    public MeetingCheckbox(final Context context, int meeting_id) {
        mid = meeting_id;
        ch = new CheckBox(context);
        ch.setText("");
    }
    public CheckBox getCheckbox() { return ch; }
    public int getMid() {
        return mid;
    }
}
