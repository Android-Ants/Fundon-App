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
import com.mysocial.flipr.models.Loan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoanApplicationRepo {

    private static final LoanApplicationRepo instance = new LoanApplicationRepo();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    RequestQueue requestQueue;
    private final String APPLY_URL = "https://codeq-flipr.herokuapp.com/api/loan/create" ;
    private final String GET_CIBIL ="https://codeq-flipr.herokuapp.com/api/cibil/get";


    public static LoanApplicationRepo getInstance() {
        return instance;
    }

    public void loanapply(Loan model, Context context) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", model.getId());
        params.put("borrowerUserName", model.getBorrowerUserName());
        params.put("borrowerEmail", model.getBorrowerEmail());
        params.put("lenderUserName", model.getLenderUserName());
        params.put("lenderEmail", model.getLenderEmail());
        params.put("status", model.getStatus());
        params.put("date", model.getDate());


        JSONObject object = new JSONObject(params);
        try {
            object.put("loanAmount", model.getLoanAmount());
            object.put("loanTenure", model.getLoanTenure());
            object.put("interestRate", model.getInterestRate());
            object.put("secured", model.getSecured());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APPLY_URL ,
                object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {
                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = context.getSharedPreferences("All Details", Context.MODE_PRIVATE);
                String tomken = sharedPreferences.getString("token", "");
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + tomken);
                params.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public void get_cibil_details ()
    {

    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

}
