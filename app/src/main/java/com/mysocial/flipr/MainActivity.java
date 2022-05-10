package com.mysocial.flipr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mysocial.flipr.authentication.SignUpActivity;

public class MainActivity extends AppCompatActivity {
    Handler h=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();

            }
        },3000);
    }
}