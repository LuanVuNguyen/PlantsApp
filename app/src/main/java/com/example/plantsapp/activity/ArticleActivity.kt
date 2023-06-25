package com.example.plantsapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plantsapp.R
import com.example.plantsapp.custom.Const
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ArticleActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_1: ImageView
    private lateinit var btn_2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        init()
    }

    private fun init() {
        btn_1 = findViewById(R.id.article1)
        btn_1.setOnClickListener(this)
        btn_2 = findViewById(R.id.article2)
        btn_2.setOnClickListener(this)
        check()
    }

    private fun check() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(Const.User)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    for (likedArticleSnapshot in dataSnapshot.child(Const.Userid).child(Const.likedarticle).children) {
                        val likedArticle: String? = likedArticleSnapshot.getValue(String::class.java)
                        if (likedArticle == Const.Writer1) {
                            btn_1.setImageResource(R.drawable.article_like)
                        } else if (likedArticle == Const.Writer2) {
                            btn_2.setImageResource(R.drawable.article_like_2)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ArticleActivity, "Load Data Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.article1 -> {
                val intent = Intent(this, TusArticleActivity::class.java)
                intent.putExtra("key", Const.article1)
                startActivity(intent)
            }
            R.id.article2 -> {
                val intent = Intent(this, TusArticleActivity::class.java)
                intent.putExtra("key", Const.article2)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
