package com.mysocial.flipr.dashboard;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.mysocial.flipr.R;
import com.mysocial.flipr.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_bottom_navigation, new DashboardFragment())
                .commit();

        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_bottom_navigation, new DashboardFragment())
                                .commit();
                        break;

                    case R.id.navigation_loans:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_bottom_navigation, new LoanFragment())
                                .commit();
                        break;

                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_activity_bottom_navigation, new ProfileFragment())
                                .commit();
                        break;
                }

                return true;
            }
        });
    }
}