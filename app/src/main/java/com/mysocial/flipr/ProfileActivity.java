package com.mysocial.flipr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    TextInputEditText name, mobile, address, occupation;
    ImageView profilephoto;
    Button proceed;
    TextView back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        name = findViewById(R.id.nameagain);
        mobile = findViewById(R.id.phnnoagain);
        address = findViewById(R.id.addressagain);
        occupation = findViewById(R.id.occagain);
        proceed = findViewById(R.id.proceed);
        profilephoto=findViewById(R.id.profilephoto);
        back1=findViewById(R.id.back1);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Map<String, String> params = new HashMap<>();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences =getSharedPreferences("All Details",MODE_PRIVATE);
                String username=sharedPreferences.getString("userName","");
                String email =sharedPreferences.getString("email","");

                params.put("email",email);
                params.put("userName",username);
                params.put("name", name.getText().toString());
                params.put("mobile",mobile.getText().toString());
                params.put("address",address.getText().toString());
                params.put("occupation", occupation.getText().toString());
                params.put("imgUrl","blah");
                Intent intent = new Intent(ProfileActivity.this, BankDetailsActivity.class);
                intent.putExtra("1st page", (Serializable) params);

//                Log.d("params", String.valueOf(params));
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ProfileActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
