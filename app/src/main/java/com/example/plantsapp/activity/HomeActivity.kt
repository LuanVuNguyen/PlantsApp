package com.example.plantsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.R;
import com.example.plantsapp.custom.Const;
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new fm_home(this))
                .commit();

        btn_add = findViewById(R.id.floatingActionButton2);
        btn_add.setOnClickListener(this);
    }

    private int backPressedCount = 0;
    private static final int DOUBLE_BACK_PRESS_COUNT = 2;

    @Override
    public void onBackPressed() {
        if (backPressedCount < DOUBLE_BACK_PRESS_COUNT - 1) {
            backPressedCount++;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        } else {
            Const.stringList.clear();
            Const.stringList2.clear();
            finishAffinity();
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