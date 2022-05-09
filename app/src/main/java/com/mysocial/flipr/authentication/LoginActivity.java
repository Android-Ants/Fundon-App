package com.mysocial.flipr.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mysocial.flipr.MainActivity;
import com.mysocial.flipr.R;
import com.mysocial.flipr.models.User;
import com.mysocial.flipr.viewmodels.SignInViewModel;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        email = findViewById(R.id.emailsignin);
        password = findViewById(R.id.passwordsignin);
        login = findViewById(R.id.login);
        initViewModel();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser();
            }
        });

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        viewModel.getUserObserver().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });
        viewModel.getTokenObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("token", s);
                startActivity(intent);
            }
        });
    }

    private void signInUser() {
        User user = new User(email.getText().toString(), password.getText().toString());
        viewModel.signInUser(user, this);
    }

    public void RegisterPage(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }
}