package com.mysocial.flipr.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysocial.flipr.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpRepo {
    private static final SignUpRepo instance = new SignUpRepo();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<String> token = new MutableLiveData<>();
    RequestQueue requestQueue;
    Boolean isRegister = false;


    public static SignUpRepo getInstance() {
        return instance;
    }

    public void usercreate(User user, Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("userName", user.getUserName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Signup_Url,
                new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {
                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                    token.postValue(response.get("token").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }) ;
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<String> getToken() {
        return token;
    }
}
