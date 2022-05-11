package com.mysocial.flipr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mysocial.flipr.models.DetailsModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BankDetailsActivity extends AppCompatActivity {
    TextInputEditText aadhar, pan,accno,ifsc;
    Button proceed;
    DetailsModel detailsmodel;
    TextView back2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankdetails);
        detailsmodel=(DetailsModel) getIntent().getSerializableExtra("abba");
        aadhar = findViewById(R.id.aadhar);
        pan = findViewById(R.id.pan);
        accno = findViewById(R.id.accno);
        ifsc = findViewById(R.id.ifsc);
        proceed = findViewById(R.id.proceed2);
        back2=findViewById(R.id.back2);

        if(detailsmodel.getAadhaarNo()!=null)
            aadhar.setText(detailsmodel.getAadhaarNo());
        if(detailsmodel.getPanNo()!=null)
            pan.setText(detailsmodel.getPanNo());
        if(detailsmodel.getBankAccountNo()!=null)
            accno.setText(detailsmodel.getBankAccountNo());
        if(detailsmodel.getIfscCode()!=null)
            ifsc.setText(detailsmodel.getIfscCode());
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BankDetailsActivity.this,ProfileActivity.class);
                intent.putExtra("abba",(Serializable)detailsmodel);
                startActivity(intent);
                finish();
            }
        });

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
                intent.putExtra("abba",(Serializable)detailsmodel);
//                Log.d("params2",params.toString());
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(BankDetailsActivity.this,ProfileActivity.class);
        intent.putExtra("abba",(Serializable) detailsmodel);
        startActivity(intent);
        finish();
    }
}
