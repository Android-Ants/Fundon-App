package com.mysocial.flipr.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mysocial.flipr.R;
import com.mysocial.flipr.models.User;
import com.mysocial.flipr.viewmodels.SignUpViewModel;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel viewModel;
    TextInputEditText name, email, password;
    Button createButton;
    TextInputLayout TILname, TILemail, TILpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        createButton = findViewById(R.id.signup);
        name = findViewById(R.id.namesignup);
        email = findViewById(R.id.emailsignup);
        password = findViewById(R.id.passwordsignup);
        TILname = findViewById(R.id.TILnamesignup);
        TILemail = findViewById(R.id.TILemailsignup);
        TILpassword = findViewById(R.id.TILpasswordsignup);

        initViewModel();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                createNewUser();
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
        User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString());
        viewModel.createNewUser(user, SignUpActivity.this);
    }

    public void LoginPage(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

    private void register() {
        String email = Objects.requireNonNull(TILemail.getEditText()).getText().toString();
        String name = Objects.requireNonNull(TILname.getEditText()).getText().toString();
        String password = Objects.requireNonNull(TILpassword.getEditText()).getText().toString();
        if (TextUtils.isEmpty(name)) {
            TILname.setError("Enter the Username");
        } else if (TextUtils.isEmpty(email)) {
            TILemail.setError("Enter your Email");
        } else if (TextUtils.isEmpty(password)) {
            TILpassword.setError("Enter the password");
        } else if (password.length() < 6) {
            TILpassword.setError("Password is too short");
        } else if (!isValidEmail(email)) {
            TILemail.setError("Invalid Email");
        } else {

            Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}