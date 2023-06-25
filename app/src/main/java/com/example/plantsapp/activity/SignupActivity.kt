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
import com.example.plantsapp.custom.FirebaseSignUpHelper
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private val firebaseSignUpHelper = FirebaseSignUpHelper()

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
                if (isStrongPassword(password)) {
                    firebaseSignUpHelper.registerUser(email, password, fullName, object :
                        FirebaseSignUpHelper.OnRegistrationCompleteListener {
                        override fun onRegistrationComplete() {
                            startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                            Toast.makeText(this@SignupActivity, "Register succeed", Toast.LENGTH_SHORT).show()

                        }

                        override fun onRegistrationFailed(errorMessage: String?) {
                            Toast.makeText(this@SignupActivity, "Register failed: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@SignupActivity, "Password consists of 11 characters with letters, numbers and special characters, lowercase and uppercase", Toast.LENGTH_SHORT).show()
                }





            }
            R.id.txt_back -> {
                onBackPressedCustom()
            }
        }
    }
    fun isStrongPassword(password: String): Boolean {
        // Mật khẩu phải có ít nhất 11 ký tự bao gồm chữ cái, số, ký tự đặc biệt, viết hoa và viết thường
        val pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{11,}$")
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
    private fun onBackPressedCustom() {
        onBackPressed()
    }


}