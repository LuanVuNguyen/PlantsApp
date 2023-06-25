package com.example.plantsapp.activity

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

class SpieceArticleActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var databaseRef: DatabaseReference
    private lateinit var txt_hagtag1: TextView
    private lateinit var txt_hagtag2: TextView
    private lateinit var txt_name: TextView
    private lateinit var txt_kingdom: TextView
    private lateinit var txt_family: TextView
    private lateinit var txt_des: TextView
    private lateinit var avt_plant: ImageView
    private lateinit var btn_like: ImageView
    private lateinit var btn_back: ImageView
    private lateinit var name: String
    private var isLiked = false
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarManager: ProgressBarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spieces_article)
        progressBar = findViewById(R.id.progressBar2)
        progressBarManager = ProgressBarManager(progressBar)
        init()
        val  itemData = intent.getStringExtra("itemData")
        databaseRef = FirebaseDatabase.getInstance().getReference().child(Const.refPlant).child(itemData.toString())
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                progressBarManager.showProgressBar()
                name = dataSnapshot.child(Const.FieldNameAdd).getValue(String::class.java)!!
                val family = dataSnapshot.child(Const.FieldFamilyAdd).getValue(String::class.java)
                val kingdom = dataSnapshot.child(Const.FieldKingdomAdd).getValue(String::class.java)
                val des = dataSnapshot.child(Const.FieldDesAdd).getValue(String::class.java)
                val hagtag1 = dataSnapshot.child(Const.FieldHagtagAdd).getValue(String::class.java)
                val hagtag2 = dataSnapshot.child(Const.FieldHagtag2Add).getValue(String::class.java)
                val image = dataSnapshot.child(Const.FieldImageAdd).getValue(String::class.java)
                txt_name.text = name
                txt_family.text = family
                txt_hagtag1.text = hagtag1
                txt_hagtag2.text = hagtag2
                txt_kingdom.text = kingdom
                txt_des.text = des
                check(name)
                Picasso.get().load(image).into(avt_plant)
                progressBarManager.hideProgressBar()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SpieceArticleActivity, "Load Data Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun check(name: String) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(Const.User)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    val likedPlants = ArrayList<String>()
                    for (likedPlantSnapshot in dataSnapshot.child(Const.Userid).child(Const.likedplants).children) {
                        val likedPlant: String? = likedPlantSnapshot.getValue(String::class.java)
                        likedPlants.add(likedPlant!!)
                    }
                    if (likedPlants.contains(name)) {
                        btn_like.setImageResource(R.drawable.like)
                        println("like")
                        isLiked = true
                    } else {
                        btn_like.setImageResource(R.drawable.unlike)
                        println("unlike")
                        isLiked = false
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi nếu có
            }
        })
    }

    private fun init() {
        txt_des = findViewById(R.id.edit_des)
        txt_family = findViewById(R.id.edit_family)
        txt_hagtag1 = findViewById(R.id.hagtag1)
        txt_hagtag2 = findViewById(R.id.hagtag2)
        txt_name = findViewById(R.id.name_article)
        avt_plant = findViewById(R.id.image_article)
        txt_kingdom = findViewById(R.id.edit_kingdom1)
        btn_like = findViewById(R.id.btn_like)
        btn_like.setOnClickListener(this)
        btn_back = findViewById(R.id.btn_plant_back)
        btn_back.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_like -> {
                val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(Const.User)
                if (!isLiked) {
                    btn_like.setImageResource(R.drawable.like)
                    Const.addStringToList(name)
                    ref.child(Const.Userid).child(Const.likedplants).setValue(Const.stringList)
                    isLiked = true
                } else {
                    btn_like.setImageResource(R.drawable.unlike)
                    Const.removeStringFromList(name)
                    ref.child(Const.Userid).child(Const.likedplants).setValue(Const.stringList)
                    isLiked = false
                }
                println(Const.stringList)
            }
            R.id.btn_plant_back -> {
                onBackPressed()
            }
        }
    }
}
