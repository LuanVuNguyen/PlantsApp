package com.example.plantsapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plantsapp.R
import com.example.plantsapp.custom.Const
import com.example.plantsapp.custom.ProgressBarManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*

class TusArticleActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var databaseRef: DatabaseReference
    private var nameperson: String? = null
    private lateinit var avt_tus: ImageView
    private lateinit var avt_persion: ImageView
    private lateinit var btn_like: ImageView
    private lateinit var btn_back: ImageView
    private lateinit var hagtag1: TextView
    private lateinit var hagtag2: TextView
    private lateinit var name_tus: TextView
    private lateinit var name_person: TextView
    private lateinit var date: TextView
    private lateinit var des: TextView
    private var isLiked = false
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarManager: ProgressBarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_article)

        init()
    }

    private fun intiview() {
        des = findViewById(R.id.edit_des)
        hagtag1 = findViewById(R.id.hagtag1)
        hagtag2 = findViewById(R.id.hagtag2)
        name_tus = findViewById(R.id.name_article)
        name_person = findViewById(R.id.name)
        date = findViewById(R.id.date)
        avt_tus = findViewById(R.id.image_article)
        avt_persion = findViewById(R.id.avt_person)
        btn_like = findViewById(R.id.btn_like)
        btn_like.setOnClickListener(this)
        btn_back = findViewById(R.id.btn_plant_back)
        btn_back.setOnClickListener(this)
    }

    private fun init() {
        progressBar = findViewById(R.id.progressBar)
        progressBarManager = ProgressBarManager(progressBar)
        progressBarManager.showProgressBar()
        intiview()
        val intent = intent
        val receivedData = intent.getStringExtra("key")
        databaseRef = FirebaseDatabase.getInstance().getReference().child(Const.refArticle).child(receivedData.toString())
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                nameperson = dataSnapshot.child(Const.FieldNamePersonArticle).getValue(String::class.java)
                val hagtag_1 = dataSnapshot.child(Const.FieldHagtagArticle).getValue(String::class.java)
                val hagtag_2 = dataSnapshot.child(Const.FieldHagtag2Article).getValue(String::class.java)
                val des_ = dataSnapshot.child(Const.FieldDesArticle).getValue(String::class.java)
                val date_ = dataSnapshot.child(Const.FieldDateArticle).getValue(String::class.java)
                val avt_tus_ = dataSnapshot.child(Const.FieldAvtTusArticle).getValue(String::class.java)
                val avt_person_ = dataSnapshot.child(Const.FieldAvtArticle).getValue(String::class.java)
                val name_tus_ = dataSnapshot.child(Const.FieldNameTusArticle).getValue(String::class.java)
                hagtag1.text = hagtag_1
                hagtag2.text = hagtag_2
                des.text = des_
                date.text = date_
                name_tus.text = name_tus_
                name_person.text = nameperson
                Picasso.get().load(avt_tus_).into(avt_tus)
                Picasso.get().load(avt_person_).into(avt_persion)
                check(nameperson)
                progressBarManager.hideProgressBar()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@TusArticleActivity, "Load Data Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun check(name: String?) {
        val databaseReference = FirebaseDatabase.getInstance().getReference(Const.User)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    val likedPlants: MutableList<String> = ArrayList()
                    for (likedPlantSnapshot in dataSnapshot.child(Const.Userid).child(Const.likedarticle).children) {
                        val likedPlant = likedPlantSnapshot.getValue(String::class.java)
                        likedPlants.add(likedPlant!!)
                    }
                    if (likedPlants.contains(name)) {
                        btn_like.setImageResource(R.drawable.like)
                        isLiked = true
                    } else {
                        btn_like.setImageResource(R.drawable.unlike)
                        isLiked = false
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi nếu có
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_like -> {
                val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(Const.User)
                if (!isLiked) {
                    btn_like.setImageResource(R.drawable.like)
                    Const.addStringToList2(nameperson!!)
                    ref.child(Const.Userid).child(Const.likedarticle).setValue(Const.stringList2)
                    isLiked = true
                } else {
                    btn_like.setImageResource(R.drawable.unlike)
                    Const.removeStringFromList2(nameperson!!)
                    ref.child(Const.Userid).child(Const.likedarticle).setValue(Const.stringList2)
                    isLiked = false
                }
            }
            R.id.btn_plant_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ArticleActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
