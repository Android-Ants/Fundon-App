package com.mysocial.flipr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.viewmodels.DetailsViewModel;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {
    DetailsViewModel viewModel;
    TextView aadharurl, panurl, bankUrl;
    boolean isVerified = false;
    Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        aadharurl = findViewById(R.id.t1);
        panurl = findViewById(R.id.t2);
        bankUrl = findViewById(R.id.t3);
        proceed = findViewById(R.id.b);
        HashMap<String, String> params = (HashMap<String, String>) getIntent().getSerializableExtra("2nd page");
//        Log.d("params3","hhh"+params.toString());
        initViewModel();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("paramsDet",params.toString());
                addDetails(params);
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(DetailsActivity.this,BankDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        viewModel.getMessageUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    private void addDetails(Map<String, String> params) {
        DetailsModel model = new DetailsModel(
                params.get("email"),params.get("userName"),
                params.get("name"), params.get("mobile"),
                params.get("address"), params.get("occupation"),
                params.get("aadhaarNo"),params.get("panNo"),
                params.get("bankAccountNo"),params.get("ifscCode"),
                params.get("imgUrl"), aadharurl.getText().toString(),
                panurl.getText().toString(),bankUrl.getText().toString(), isVerified);

        viewModel.createdetails(model, DetailsActivity.this);
    }
}