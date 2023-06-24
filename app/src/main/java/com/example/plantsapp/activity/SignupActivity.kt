package com.example.plantsapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plantsapp.R
import com.example.plantsapp.custom.FirebaseHelper


class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private val firebaseHelper = FirebaseHelper()

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtFullName: EditText
    private lateinit var btnSignUp: ImageView
    private lateinit var txtBack: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        init()
    }

    private fun init() {
        txtEmail = findViewById(R.id.txt_signup_email)
        txtPassword = findViewById(R.id.txt_signup_password)
        txtFullName = findViewById(R.id.txt_signup_personname)
        btnSignUp = findViewById(R.id.btn_signup)
        txtBack = findViewById(R.id.txt_back)

        btnSignUp.setOnClickListener(this)
        txtBack.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_signup -> {
                val email = txtEmail.text.toString()
                val password = txtPassword.text.toString()
                val fullName = txtFullName.text.toString()

                firebaseHelper.registerUser(email, password, fullName, object :
                    FirebaseHelper.OnRegistrationCompleteListener {
                    override fun onRegistrationComplete() {
                        // Xử lý khi đăng ký thành công
                        startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                        Toast.makeText(this@SignupActivity, "Register succeed", Toast.LENGTH_SHORT).show()
                        // Chuyển đến trang HomeActivity hoặc màn hình chính của ứng dụng


                    }

                    override fun onRegistrationFailed(errorMessage: String?) {
                        // Xử lý khi đăng ký thất bại
                        Toast.makeText(this@SignupActivity, "Register failed: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                })

            }
            R.id.txt_back -> {
                onBackPressedCustom()
            }
        }
    }

    private fun onBackPressedCustom() {
        onBackPressed()
    }


}