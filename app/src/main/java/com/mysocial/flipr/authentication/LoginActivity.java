package com.mysocial.flipr.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.mysocial.flipr.R;
import com.mysocial.flipr.dashboard.DashboardActivity;
import com.mysocial.flipr.models.User;
import com.mysocial.flipr.viewmodels.SignInViewModel;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userName, password;
    Button login;
    SignInViewModel viewModel;
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        sharedPreferences = getSharedPreferences("Fundon",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        userName = findViewById(R.id.usernamesignin);
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
                editor.putString("token" , s);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("token", s);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signInUser() {
        User user = new User(userName.getText().toString(), password.getText().toString());
        viewModel.signInUser(user, this);
    }

    public void RegisterPage(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }
}