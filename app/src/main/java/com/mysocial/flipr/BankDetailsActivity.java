package com.mysocial.flipr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BankDetailsActivity extends AppCompatActivity {
    TextInputEditText aadhar, pan,accno,ifsc;
    Button proceed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankdetails);
        aadhar = findViewById(R.id.aadhar);
        pan = findViewById(R.id.pan);
        accno = findViewById(R.id.accno);
        ifsc = findViewById(R.id.ifsc);
        proceed = findViewById(R.id.proceed2);


        HashMap<String,String> params= (HashMap<String, String>) getIntent().getSerializableExtra("1st page");
//        Log.d("Params1st",params.toString());
//        Log.d("Params1",params.get("name"));
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params.put("aadhaarNo", aadhar.getText().toString());
                params.put("panNo",pan.getText().toString());
                params.put("bankAccountNo",accno.getText().toString());
                params.put("ifscCode", ifsc.getText().toString());
                Intent intent = new Intent(BankDetailsActivity.this, DetailsActivity.class);
                intent.putExtra("2nd page", (Serializable) params);

//                Log.d("params2",params.toString());

                startActivity(intent);
                finish();
            }
        });
    }
}
