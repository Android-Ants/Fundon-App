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
import com.mysocial.flipr.models.LoanApplicationModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoanApplicationRepo {
    private static final LoanApplicationRepo instance = new LoanApplicationRepo();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    RequestQueue requestQueue;


    public static LoanApplicationRepo getInstance() {
        return instance;
    }

    public void loanapply(LoanApplicationModel model, Context context) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", model.getId());
        params.put("borrowerUserName", model.getBorrowerUserName());
        params.put("borrowerEmail", model.getBorrowerEmail());
        params.put("lenderUserName", model.getLenderUserName());
        params.put("lenderEmail", model.getLenderEmail());
        params.put("loanAmount", model.getLoanAmount());
        params.put("loanTenure", model.getLoanTenure());
        params.put("interestRate", model.getInterestRate());
        params.put("status", model.getStatus());
        params.put("secured", model.getSecured());
        params.put("date", model.getDate());

        String url = "https://codeq-flipr.herokuapp.com/api/loan/create";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
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
//                Log.d("token",tomken);
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + tomken);
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
