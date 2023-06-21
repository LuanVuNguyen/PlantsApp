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

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var btn_login: ImageView? = null
    private var btn_facebook: ImageView? = null
    private var btn_google: ImageView? = null
    private var txt_email: EditText? = null
    private var txt_password: EditText? = null
    private var txt_create_account: TextView? = null
    private var txt_reset: TextView? = null

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        init()
    }

    private fun init() {
        btn_login = findViewById(R.id.btn_login)
        btn_login?.setOnClickListener(this)
        btn_facebook = findViewById(R.id.btn_facebook)
        btn_facebook?.setOnClickListener(this)
        btn_google = findViewById(R.id.btn_google)
        btn_google?.setOnClickListener(this)
        txt_email = findViewById(R.id.txt_Login_email)
        txt_password = findViewById(R.id.txt_login_password)
        txt_create_account = findViewById(R.id.txt_back)
        txt_create_account?.setOnClickListener(this)
        txt_reset = findViewById(R.id.txt_reset)
        txt_reset?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val email = txt_email?.text.toString()
                val password = txt_password?.text.toString()
                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(this, "Email and Password does not emty", Toast.LENGTH_SHORT).show()
                }
                else {
                    signInWithEmailAndPassword(email, password)
                }
            }
            R.id.btn_facebook -> {
                Toast.makeText(this, "update next version ", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_google -> {
                Toast.makeText(this, "update next version", Toast.LENGTH_SHORT).show()
            }
            R.id.txt_back -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }
            R.id.txt_reset -> {
                startActivity(Intent(this, ResetActicity::class.java))
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = mAuth.currentUser
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
