package com.laioffer.matrix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

public class ControlPanel extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.baseline_home_black_18dp);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                if(menuItem.getItemId() == R.id.drawer_logout) {
                    Config.username = null;
                    logout();
                }
                return true;
            }
        });

        final LocationTracker locationTracker = new LocationTracker(this);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                final TextView usernameTextView = drawerView.findViewById(R.id.user_name);
                final TextView locationTextView = drawerView.findViewById(R.id.user_location);
                locationTracker.getLocation();
                final double longitude = locationTracker.getLongitude();
                final double latitude = locationTracker.getLatitude();
                if(Config.username == null) {
                    usernameTextView.setText("");
                    locationTextView.setText("");
                } else {
                    usernameTextView.setText(Config.username);
                    locationTextView.setText("Lat=" + new DecimalFormat(".##").
                            format(latitude) + ",Lon=" + new DecimalFormat(".##").
                            format(longitude));

                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        // add Fragment to the activity
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MainFragment.newInstance()).commit();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.CHECK_LOGIN, false);
        editor.putString(Config.USER_NAME, null);
        editor.putString(Config.USER_PASSWORD, null);
        editor.commit();
        finish();
        startActivity(new Intent(this, OnBoardingActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
