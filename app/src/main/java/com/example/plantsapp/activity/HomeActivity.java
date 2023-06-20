package com.example.plantsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.R;
import com.example.plantsapp.fagment.fm_add;
import com.example.plantsapp.fagment.fm_home;
import com.example.plantsapp.fagment.fm_profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    FloatingActionButton btn_add;
    private Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Set the default selected fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new fm_home(this))
                .commit();

        btn_add = findViewById(R.id.floatingActionButton2);
        btn_add.setOnClickListener(this);
        hideSystemNavigationBar();
    }
    private void hideSystemNavigationBar() {
        // Kiểm tra phiên bản Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Ẩn thanh điều hướng hệ thống
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (fragment instanceof fm_home) {
            selectedFragment = new fm_home(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, selectedFragment)
                    .commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                System.out.println("profile");
                selectedFragment = new fm_home(this);
                break;
            case R.id.person:
                System.out.println("profile");
                selectedFragment = new fm_profile();
                break;
            case R.id.placeholder:
                selectedFragment = new fm_add(this);
                break;
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, selectedFragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.floatingActionButton2){
            selectedFragment = new fm_add(this);
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, selectedFragment)
                        .commit();
            }
        }
    }

}