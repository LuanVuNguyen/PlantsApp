package com.example.plantsapp.custom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.plantsapp.activity.StartedActivity


class SplashTheme : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val intent = Intent(this@SplashTheme, StartedActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}