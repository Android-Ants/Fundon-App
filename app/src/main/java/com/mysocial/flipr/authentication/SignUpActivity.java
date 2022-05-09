package com.mysocial.flipr.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mysocial.flipr.R;
import com.mysocial.flipr.models.User;
import com.mysocial.flipr.viewmodels.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel viewModel;
    EditText name, email, password;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        createButton = findViewById(R.id.signup);
        name = findViewById(R.id.namesignup);
        email = findViewById(R.id.emailsignup);
        password = findViewById(R.id.passwordsignup);

        initViewModel();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUser();
                Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        viewModel.getMessageUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        viewModel.getTokenUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }
    private void createNewUser() {
        User user = new User(name.getText().toString(),email.getText().toString(),password.getText().toString());
        viewModel.createNewUser(user,SignUpActivity.this);
    }

    public void LoginPage(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }
}