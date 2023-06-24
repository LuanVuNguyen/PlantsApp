package com.example.plantsapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.plantsapp.R
import com.google.firebase.auth.FirebaseAuth

class ResetPassActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var txtEmail: EditText
    private lateinit var btnReset: ImageView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        init()

        btnReset.setOnClickListener(this)
    }

    private fun init() {
        txtEmail = findViewById(R.id.txt_reset_email)
        btnReset = findViewById(R.id.btn_sendemail)
        auth = FirebaseAuth.getInstance()
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@ResetPassActivity, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorMessage = task.exception?.message
                    Toast.makeText(this@ResetPassActivity, "Password reset failed: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_sendemail) {
            val email = txtEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this@ResetPassActivity, "Please enter email address", Toast.LENGTH_SHORT).show()
            } else {
                resetPassword(email)
            }
        }
    }
}