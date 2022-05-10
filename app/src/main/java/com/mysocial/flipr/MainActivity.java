package com.mysocial.flipr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.mysocial.flipr.authentication.SignUpActivity;
import com.mysocial.flipr.dashboard.DashboardActivity;

public class MainActivity extends AppCompatActivity {
    Handler h=new Handler();
    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Fundon",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                if ( !sharedPreferences.getString("token" , "ansh").equalsIgnoreCase("ansh") )
                {
                    Intent intent=new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);
    }
}