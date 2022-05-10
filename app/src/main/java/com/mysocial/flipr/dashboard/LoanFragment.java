package com.mysocial.flipr.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.mysocial.flipr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoanFragment extends Fragment {

    private RequestQueue requestQueue ;
    private Context context ;
    private final String URL = "https://codeq-flipr.herokuapp.com/api/loan/allLoans";
    private String token ;
    private final String TAG = "LoanFragment";

    public LoanFragment ()
    {

    }

    public LoanFragment( Context context , String token ) {

        this.context = context ;
        this.token = token ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan, container, false);
        get_all_loans();
        return view ;
    }

    private void get_all_loans ()
    {
        requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG , response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("response");

                    for ( int i=0 ; i < array.length() ; i++ )
                    {
                        JSONObject object2 = array.getJSONObject(i);
                        
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + token);
                params.put("Content-Type", "application/json; charset=utf-8");
                return params ;
            }
        };

        requestQueue.add(stringRequest);
    }

}