package com.mysocial.flipr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.mysocial.flipr.dashboard.DashboardActivity;
import com.mysocial.flipr.models.DetailsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    TextInputEditText name, mobile, address, occupation;
    ImageView profilephoto;
    Button proceed;
    DetailsModel detailsmodel;
    TextView back1;
    private String string = "create";
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        detailsmodel = (DetailsModel) getIntent().getSerializableExtra("abba");
        string = getIntent().getStringExtra("string");
        name = findViewById(R.id.nameagain);
        mobile = findViewById(R.id.phnnoagain);
        address = findViewById(R.id.addressagain);
        occupation = findViewById(R.id.occagain);
        proceed = findViewById(R.id.proceed);
        profilephoto = findViewById(R.id.profilephoto);
        back1 = findViewById(R.id.back1);

        if ( string.equalsIgnoreCase("modify" ))
        {
            if (detailsmodel.getName() != null)
                name.setText(detailsmodel.getName());
            if (detailsmodel.getMobile() != null)
                mobile.setText(detailsmodel.getMobile());
            if (detailsmodel.getAddress() != null)
                address.setText(detailsmodel.getAddress());
            if (detailsmodel.getOccupation() != null)
                occupation.setText(detailsmodel.getOccupation());
        }

        if (string.equalsIgnoreCase("modify")) {
            proceed.setText("Update");
            name.setFocusable(false);
            name.setClickable(false);
        }

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Map<String, String> params = new HashMap<>();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (string.equalsIgnoreCase("create")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("All Details", MODE_PRIVATE);
                    String username = sharedPreferences.getString("userName", "");
                    String email = sharedPreferences.getString("email", "");

                    params.put("email", email);
                    params.put("userName", username);
                    params.put("name", name.getText().toString());
                    params.put("mobile", mobile.getText().toString());
                    params.put("address", address.getText().toString());
                    params.put("occupation", occupation.getText().toString());
                    params.put("imgUrl", "blah");
                    Intent intent = new Intent(ProfileActivity.this, BankDetailsActivity.class);
                    intent.putExtra("1st page", (Serializable) params);
                    intent.putExtra("abba", (Serializable) detailsmodel);

                    startActivity(intent);
                    finish();
                } else {
                    update_details(detailsmodel);
                }
            }
        });

    }

    private void update_details(DetailsModel model) {

        Map<String, String> params = new HashMap<>();
        params.put("email", model.getEmail());
        params.put("userName", model.getUserName());
        params.put("name", model.getName());
        params.put("mobile", mobile.getText().toString());
        params.put("address", address.getText().toString());
        params.put("occupation", occupation.getText().toString());
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

        String url2 = "https://codeq-flipr.herokuapp.com/api/profile/modify";
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, url2,
                new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, response.get("message").toString(), Toast.LENGTH_SHORT).show();
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
                SharedPreferences sharedPreferences = getSharedPreferences("All Details", Context.MODE_PRIVATE);
                String tomken = sharedPreferences.getString("token", "");
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + tomken);
                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest2);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
