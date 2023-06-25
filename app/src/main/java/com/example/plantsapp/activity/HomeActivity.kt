package com.example.plantsapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.plantsapp.R
import com.example.plantsapp.custom.Const
import com.example.plantsapp.fagment.fm_add
import com.example.plantsapp.fagment.fm_home
import com.example.plantsapp.fagment.fm_profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private lateinit var btn_add: FloatingActionButton
    private var selectedFragment: Fragment? = null
    private var backPressedCount = 0

    companion object {
        private const val DOUBLE_BACK_PRESS_COUNT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fm_home(this))
            .commit()

        btn_add = findViewById(R.id.floatingActionButton2)
        btn_add.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (backPressedCount < DOUBLE_BACK_PRESS_COUNT - 1) {
            backPressedCount++
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show()
        } else {
            Const.stringList.clear()
            Const.stringList2.clear()
            finishAffinity()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectedFragment = when (item.itemId) {
            R.id.home -> {
                println("profile")
                fm_home(this)
            }
            R.id.person -> {
                println("profile")
                fm_profile()
            }
            R.id.placeholder -> fm_add(this)
            else -> null
        }

        selectedFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, it)
                .commit()
            return true
        }
        return false
    }

    override fun onClick(v: View) {
        if (v.id == R.id.floatingActionButton2) {
            selectedFragment = fm_add(this)
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, it)
                    .commit()
            }
        }
    }
}
