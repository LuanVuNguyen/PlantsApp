package com.example.plantsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var btn_login: ImageView? = null
    var btn_facebook: ImageView? = null
    var btn_google: ImageView? = null
    var txt_email: EditText? = null
    var txt_password: EditText? = null
    var txt_create_account: TextView? = null
    var txt_reset: TextView? = null
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun init() {
        btn_login = findViewById<View>(R.id.btn_signup) as ImageView
        btn_login!!.setOnClickListener(this)
        btn_facebook = findViewById<View>(R.id.btn_facebook) as ImageView
        btn_facebook!!.setOnClickListener(this)
        btn_google = findViewById<View>(R.id.btn_google) as ImageView
        btn_google!!.setOnClickListener(this)
        txt_email = findViewById<View>(R.id.txt_Login_email) as EditText
        txt_password = findViewById<View>(R.id.txt_login_password) as EditText
        txt_create_account = findViewById<View>(R.id.txt_back) as TextView
        txt_create_account!!.setOnClickListener(this)
        txt_reset = findViewById<View>(R.id.txt_reset) as TextView
        txt_reset!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_signup -> {
                val email = txt_email?.text.toString()
                val password = txt_password?.text.toString()
                // Kiểm tra email và password trên Authentication
                val auth = FirebaseAuth.getInstance()
                val credentials = EmailAuthProvider.getCredential(email, password)
                auth.signInWithCredential(credentials)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Đăng nhập thành công
                            Toast.makeText(this@LoginActivity, "Log In Succeed", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        } else {
                            val exception = task.exception
                            if (exception is FirebaseAuthInvalidCredentialsException) {
                                // Sai email hoặc password

                                Toast.makeText(this@LoginActivity, "Wrong email or password, please enter correct email and password", Toast.LENGTH_SHORT).show()

                            } else {

                                progressBar.visibility = View.VISIBLE
                                Toast.makeText(this@LoginActivity, "Account not registered", Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
            }
            R.id.btn_facebook -> {
                println("facebook")
            }
            R.id.btn_google -> {
                println("google")
            }
            R.id.txt_back -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }
            R.id.txt_reset -> {
                startActivity(Intent(this, ResetPassActivity::class.java))
            }
        }
    }
}