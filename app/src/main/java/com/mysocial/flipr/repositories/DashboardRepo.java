package com.mysocial.flipr.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mysocial.flipr.models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DashboardRepo {

    private final MutableLiveData<DetailsModel> profileDetails = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private static final DashboardRepo instance = new DashboardRepo() ;
    private RequestQueue requestQueue;
    private final String GET_PROFILE_URL = "https://codeq-flipr.herokuapp.com/api/profile/get";

    public static DashboardRepo getInstance() {
        return instance ;
    }

    public void get_profile ( String username ,  Context context , String token )
    {
        Map<String, String> params = new HashMap<>();
        params.put("userName", username);

        JSONObject object = new JSONObject(params) ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GET_PROFILE_URL,
               object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {
                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                    message.postValue(response.getString("message"));


                    if ( response.getString("message").equalsIgnoreCase("Profile Found") )
                    {
                        JSONObject object = response.getJSONObject("profile");

                        DetailsModel detailsModel = new DetailsModel();
                        detailsModel.setEmail(object.getString("email"));
                        detailsModel.setUserName(object.getString("userName"));
                        detailsModel.setName(object.getString("name"));
                        detailsModel.setMobile(object.getString("mobile"));
                        detailsModel.setAddress(object.getString("address"));
                        detailsModel.setOccupation(object.getString("occupation"));
                        detailsModel.setAadhaarNo(object.getString("aadhaarNo"));
                        detailsModel.setPanNo(object.getString("panNo"));
                        detailsModel.setBankAccountNo(object.getString("bankAccountNo"));
                        detailsModel.setIfscCode(object.getString("ifscCode"));
                        detailsModel.setImgUrl(object.getString("imgUrl"));
                        detailsModel.setAadhaarUrl(object.getString("aadhaarUrl"));
                        detailsModel.setPanUrl(object.getString("panUrl"));
                        detailsModel.setBankUrl(object.getString("bankUrl"));
                        detailsModel.setVerified(object.getBoolean("isVerified"));

                        profileDetails.postValue(detailsModel);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                message.postValue("Error : "  + error.toString());
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        Log.d("error", res);
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                params.put("Content-Type", "application/json; charset=utf-8");
                return params ;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<DetailsModel> getProfileDetails() {
        return profileDetails;
    }

}
