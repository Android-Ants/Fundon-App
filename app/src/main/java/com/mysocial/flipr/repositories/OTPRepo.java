package com.mysocial.flipr.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysocial.flipr.Utils.Constants;
import com.mysocial.flipr.models.OTPModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OTPRepo {
    private static final OTPRepo instance = new OTPRepo();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<String> user = new MutableLiveData<>();
    RequestQueue requestQueue;


    public static OTPRepo getInstance() {
        return instance;
    }

    public void VerifyUser(OTPModel otpModel, Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("token", otpModel.getToken());
        params.put("otp", otpModel.getOtp());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Verify_Email,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("OTP Repo", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<String> getUser() {
        return user;
    }
}
