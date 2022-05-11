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
import com.mysocial.flipr.cibil.User;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.models.Loan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoanApplicationRepo {

    private static final LoanApplicationRepo instance = new LoanApplicationRepo();
    private final MutableLiveData<String> messageLoanApply = new MutableLiveData<>();
    private final MutableLiveData<String> messageCibil = new MutableLiveData<>();
    private final MutableLiveData<User> cibil = new MutableLiveData<>();
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
                    messageLoanApply.postValue(response.get("message").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                messageLoanApply.postValue(error.toString());
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

    public void get_cibil_details (Context context , DetailsModel detailsModel )
    {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", detailsModel.getUserName());
        params.put("userEmail", detailsModel.getEmail());


        JSONObject object = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GET_CIBIL ,
                object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {
                    messageCibil.postValue(response.get("message").toString());

                    JSONObject object1 = response.getJSONObject("cibil");
                    User user = new User();
                    user.setLoanCount(object1.getInt("loanCount"));
                    user.setCurrentLoanCount(object1.getInt("currentLoanCount"));
                    user.setFinishedOverDue(object1.getInt("finishedOverdue"));
                    user.setCurrentLoanCount(object1.getInt("currentOverdue"));
                    user.setSecuredLoanCount(object1.getInt("securedLoanCount"));
                    user.setUnsecredLoanCount(object1.getInt("unsecuredLoanCount"));
                    user.setLoanCountYear(object1.getInt("loanCountYear"));
                    user.setDisapprovedCount(object1.getInt("disapprovedLoanCount"));
                    user.setTotalLoanCredited(object1.getInt("totalLoanCredited"));
                    user.setPresentLoanAmount(object1.getInt("presentLoanAmount"));
                    user.setAmountPaid(object1.getInt("amountPaid"));

                    cibil.postValue(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                messageCibil.postValue(error.toString());
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

    public MutableLiveData<String> getMessageLoanApply() {
        return messageLoanApply;
    }
    public MutableLiveData<String> getMessageCibil() {
        return messageCibil;
    }

    public MutableLiveData<User> getCibil() {
        return cibil;
    }

}
