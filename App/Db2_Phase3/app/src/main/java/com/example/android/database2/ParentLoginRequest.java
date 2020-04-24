package com.example.android.database2;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ParentLoginRequest extends StringRequest {
    private Map<String, String> args;
    private static Response.ErrorListener err = new Response.ErrorListener()
    {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("LoginParent", "Error listener response: " + error.getMessage());
        }
    };

    // Must have url passed in as arg to make use of string resources without using contexts
    public ParentLoginRequest(String email, String password, String url, Response.Listener<String> listener){
        super(Request.Method.POST, url, listener, err);
        args = new HashMap<String, String>();
        args.put("email", email);
        args.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return args;
    }
}
