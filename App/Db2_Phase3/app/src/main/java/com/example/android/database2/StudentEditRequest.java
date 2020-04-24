package com.example.android.database2;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StudentEditRequest extends StringRequest {
    private Map<String, String> args;
    private static Response.ErrorListener err = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("EditStudent", "Error listener response: " + error.getMessage());
        }
    };

    // Must have url passed in as arg to make use of string resources without using contexts
    public StudentEditRequest(String email,  String password, String name, String phone, String grade,
                              boolean changeEmail, boolean changePassword, boolean changeName, boolean changePhone,
                              boolean changeGrade, int id, String url, Response.Listener<String> listener){
        super(Request.Method.POST, url, listener, err);
        args = new HashMap<String, String>();
        args.put("email", email);
        args.put("password", password);
        args.put("name", name);
        args.put("phone", phone);
        args.put("grade", grade);
        args.put("cemail", String.valueOf(changeEmail));
        args.put("cpassword", String.valueOf(changePassword));
        args.put("cname", String.valueOf(changeName));
        args.put("cphone", String.valueOf(changePhone));
        args.put("cgrade", String.valueOf(changeGrade));
        args.put("id", String.valueOf(id));
    }

    @Override
    public Map<String, String> getParams() {
        return args;
    }
}
