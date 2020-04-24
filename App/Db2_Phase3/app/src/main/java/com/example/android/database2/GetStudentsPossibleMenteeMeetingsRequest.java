package com.example.android.database2;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetStudentsPossibleMenteeMeetingsRequest extends StringRequest {
    private Map<String, String> args;
    private static Response.ErrorListener err = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("GetPMenteeMeetings", "Error listener response: " + error.getMessage());
        }
    };

    // Must have url passed in as arg to make use of string resources without using contexts
    public GetStudentsPossibleMenteeMeetingsRequest(int sid, String url, Response.Listener<String> listener){
        super(Request.Method.POST, url, listener, err);
        args = new HashMap<String, String>();
        args.put("sid", String.valueOf(sid));
    }

    @Override
    public Map<String, String> getParams() {
        return args;
    }
}
