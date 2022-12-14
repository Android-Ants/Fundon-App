package com.mysocial.flipr.repositories;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysocial.flipr.Utils.Constants;
import com.mysocial.flipr.models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsRepo {
    private static final DetailsRepo instance = new DetailsRepo();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    ProgressDialog progressDialog;

    public static DetailsRepo getInstance() {
        return instance;
    }

    public void detailscreate(DetailsModel model, Context context) {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Updating Your Profile . Please Wait");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("email", model.getEmail());
        params.put("userName", model.getUserName());
        params.put("name", model.getName());
        params.put("mobile", model.getMobile());
        params.put("address", model.getAddress());
        params.put("occupation", model.getOccupation());
        params.put("aadhaarNo", model.getAadhaarNo());
        params.put("panNo", model.getPanNo());
        params.put("bankAccountNo", model.getBankAccountNo());
        params.put("ifscCode", model.getIfscCode());
        params.put("imgUrl", model.getImgUrl());
        params.put("aadhaarUrl", model.getAadhaarUrl());
        params.put("panUrl", model.getPanUrl());
        params.put("bankUrl", model.getBankUrl());
        params.put("isVerified", String.valueOf(model.isVerified()));
        Log.d("params", String.valueOf(params));


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.CREATE_PROFILE,
                new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
//                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                    message.postValue(response.get("message").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences= context.getSharedPreferences("All Details",Context.MODE_PRIVATE);
                String tomken=sharedPreferences.getString("token","");
//                Log.d("token",tomken);
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+tomken);
                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }


}
