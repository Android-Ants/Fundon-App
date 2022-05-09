package com.mysocial.flipr.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.mysocial.flipr.MainActivity;
import com.mysocial.flipr.R;
import com.mysocial.flipr.models.OTPModel;
import com.mysocial.flipr.viewmodels.OTPViewModel;
import com.mysocial.flipr.viewmodels.SignUpViewModel;

public class OTPActivity extends AppCompatActivity {

    private TextInputEditText otp;
    private Button verify_otp;
    private OTPViewModel viewModel;
    private SignUpViewModel signUpViewModel;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp = findViewById(R.id.otp);
        verify_otp = findViewById(R.id.login_otp);
        initViewModel();

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify_email_address();

                startActivity(new Intent(OTPActivity.this, MainActivity.class));
            }
        });
    }

    private void verify_email_address() {
        OTPModel model = new OTPModel(token,otp.getText().toString());
        viewModel.verifyUser(model, OTPActivity.this);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(OTPViewModel.class);
        viewModel.getMessageObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        viewModel.getUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        signUpViewModel=new ViewModelProvider(this).get(SignUpViewModel.class);
        signUpViewModel.getTokenUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                token=s;
            }
        });
    }
}