package com.mysocial.flipr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysocial.flipr.cibil.User;
import com.mysocial.flipr.databinding.ActivityReminderBinding;
import com.mysocial.flipr.models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReminderActivity extends AppCompatActivity {

    private ActivityReminderBinding binding ;
    private DetailsModel detailsModel ;
    private RequestQueue requestQueue ;
    private ProgressDialog progressDialog ;
    private final String URL = "https://codeq-flipr.herokuapp.com/api/loan/sendRemainder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReminderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        detailsModel = (DetailsModel) getIntent().getSerializableExtra("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");

        binding.sendReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( check_empty() )
                {
                    progressDialog.show();
                    send_remainder(detailsModel);
                }
            }
        });
    }

    private Boolean check_empty ()
    {
        if ( binding.title.getText().toString().isEmpty() )
        {
            binding.title.setError("Required");
            return false ;
        }
        else if ( binding.message.getText().toString().isEmpty() ) {
            binding.message.setError("Required");
            return false;
        }
        return true ;
    }

    private void send_remainder ( DetailsModel detailsModel )
    {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", detailsModel.getUserName());
        params.put("email", binding.title.getText().toString());
        params.put("message", binding.message.getText().toString());


        JSONObject object = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL ,
                object, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("response", response.toString());
                try {
                    Toast.makeText(ReminderActivity.this, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                binding.title.getText().clear();
                binding.message.getText().clear();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("All Details", Context.MODE_PRIVATE);
                String tomken = sharedPreferences.getString("token", "");
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + tomken);
                params.put("Content-Type", "application/json; charset=utf-8");
                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


}
