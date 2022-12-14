package com.mysocial.flipr.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysocial.flipr.Utils.Constants;
import com.mysocial.flipr.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRepo {

    private static final LoginRepo instance = new LoginRepo();
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<String> token = new MutableLiveData<>();
    RequestQueue requestQueue;


    public static LoginRepo getInstance() {
        return instance;
    }

    public void userSignIn(User user, Context context) {

        Map<String, String> params = new HashMap<>();
        params.put("userName", user.getUserName());
        params.put("password", user.getPassword());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Signin_URL,
                new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    token.postValue(response.get("token").toString());
//                    Log.d("token",response.get("token").toString());

                    SharedPreferences sharedPreferences= context.getSharedPreferences("All Details",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("token",response.get("token").toString());
                    JSONObject details=(JSONObject) response.get("user");
                    editor.putString("userName",details.get("userName").toString());
                    editor.putString("email",details.get("email").toString());

                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getToken() {
        return token;
    }

}
