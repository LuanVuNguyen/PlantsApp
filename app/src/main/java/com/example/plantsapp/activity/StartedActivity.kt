package com.example.plantsapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.plantsapp.R


class StartedActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_getstarted: ImageView
    private lateinit var e1: ImageView
    private lateinit var e2: ImageView
    private lateinit var e3: ImageView
    private lateinit var txt_title: TextView
    private lateinit var txt_content: TextView

    private lateinit var handler_title: Handler
    private lateinit var handler_content: Handler
    private lateinit var handler: Handler
    private lateinit var runnable_title: Runnable
    private lateinit var runnable_content: Runnable
    private lateinit var runnable: Runnable
    private var currentIndex = 0



    private val title = arrayOf(
            "IDENTIFY PLANTS",
            "Learn Many Plants Species",
            "Read Many Articles About Plant"
    )

    private val content = arrayOf(
            "You can identify the plants you don't know \n" +
                    "through your camera",
            "Let's learn about the many plant species that \n" +
                    "exist in this world",
            "Let's learn more about beautiful plants and read many articles about plants and gardening"
    )
    private val img = intArrayOf(R.drawable.egreen, R.drawable.egreen, R.drawable.egreen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isGetStartedShown = sharedPreferences.getBoolean("isGetStartedShown", false)
        if (isGetStartedShown) {
            val getStartedIntent = Intent(this@StartedActivity, LoginActivity::class.java)
            startActivity(getStartedIntent)

            val editor = sharedPreferences.edit()
            editor.putBoolean("isGetStartedShown", true)
            editor.apply()
        }

    }

    private fun init() {
        btn_getstarted = findViewById(R.id.btn_started)
        btn_getstarted.setOnClickListener(this)

        txt_title = findViewById(R.id.txt_tilte)
        txt_content = findViewById(R.id.txt_content)

        e1 = findViewById(R.id.e1)
        e2 = findViewById(R.id.e2)
        e3 = findViewById(R.id.e3)

        handler_title = Handler(Looper.getMainLooper())
        handler_content = Handler(Looper.getMainLooper())
        handler = Handler(Looper.getMainLooper())

        runnable_title = object : Runnable {
            var currentIndex = 0

            override fun run() {
                txt_title.text = title[currentIndex]
                currentIndex = (currentIndex + 1) % title.size
                handler_title.postDelayed(this, 3000)
            }
        }

        runnable_content = object : Runnable {
            var currentIndex = 0

            override fun run() {
                txt_content.text = content[currentIndex]
                currentIndex = (currentIndex + 1) % content.size
                handler_content.postDelayed(this, 3000)
            }
        }

        runnable = object : Runnable {
            override fun run() {
                currentIndex = (currentIndex + 1) % img.size
                when (currentIndex) {
                    0 -> {
                        showImageView(e1)
                        hideImageView(e2)
                        hideImageView(e3)
                    }
                    1 -> {
                        showImageView(e2)
                        hideImageView(e1)
                        hideImageView(e3)
                    }
                    2 -> {
                        showImageView(e3)
                        hideImageView(e2)
                        hideImageView(e1)
                    }
                }
                handler.postDelayed(this, 3000)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        handler_title.postDelayed(runnable_title, 1000)
        handler_content.postDelayed(runnable_content, 1000)
        handler.postDelayed(runnable, 1000)
    }

    override fun onStop() {
        super.onStop()
        handler_title.removeCallbacks(runnable_title)
        handler_content.removeCallbacks(runnable_content)
        handler.removeCallbacks(runnable)
    }
    private fun showImageView(imageView: ImageView) {
        imageView.setImageResource(R.drawable.egreen)
    }

    private fun hideImageView(imageView: ImageView) {
        imageView.setImageResource(R.drawable.ewhite)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_started -> {
                val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val isGetStartedShown = sharedPreferences.getBoolean("isGetStartedShown", false)
                if (!isGetStartedShown) {
                    val getStartedIntent = Intent(this@StartedActivity, LoginActivity::class.java)
                    startActivity(getStartedIntent)

                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isGetStartedShown", true)
                    editor.apply()
                }

            }
        }
    }

}