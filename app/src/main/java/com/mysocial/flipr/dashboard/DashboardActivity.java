package com.mysocial.flipr.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;
import com.mysocial.flipr.ProfileActivity;
import com.mysocial.flipr.R;
import com.mysocial.flipr.databinding.ActivityDashboardBinding;
import com.mysocial.flipr.models.DetailsModel;
import com.mysocial.flipr.viewmodels.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private ProgressDialog progressDialog;
    private DashboardViewModel dashboardViewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DetailsModel detailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Your Profile . Please Wait ...");

        sharedPreferences = getSharedPreferences("Fundon", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        detailsModel = new DetailsModel();
        init_view_model();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_bottom_navigation,
                        new LoanFragment(DashboardActivity.this, sharedPreferences.getString("token", ""), detailsModel))
                .commit();

        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_bottom_navigation, new DashboardFragment( detailsModel , DashboardActivity.this  , sharedPreferences.getString("token", "") ))
                                .commit();
                        break;

                    case R.id.navigation_loans:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_bottom_navigation,
                                        new LoanFragment(DashboardActivity.this, sharedPreferences.getString("token", ""), detailsModel))
                                .commit();
                        break;

                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_bottom_navigation, new ProfileFragment(detailsModel))
                                .commit();
                        break;
                }

                return true;
            }
        });
    }

    private void init_view_model() {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        get_profile();
        dashboardViewModel.getMessageUserObserver().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                if (s.equalsIgnoreCase("Profile Found")) {
                    Toast.makeText(DashboardActivity.this, "Profile Fetching Completed", Toast.LENGTH_SHORT).show();
                    dashboardViewModel.getProfileObserver().observe(DashboardActivity.this, new Observer<DetailsModel>() {
                        @Override
                        public void onChanged(DetailsModel detailModel) {
                            detailsModel = detailModel;
                        }
                    });
                } else {
                    Toast.makeText(DashboardActivity.this, "First Complete Profile", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void get_profile() {
        progressDialog.show();
        dashboardViewModel.createNewUser(sharedPreferences.getString("userName", ""), DashboardActivity.this
                , sharedPreferences.getString("token", ""));
    }

}