package com.example.plantsapp.custom

import android.content.Context
import android.widget.Toast
import com.example.plantsapp.custom.Const.addStringToList2
import com.google.firebase.database.*

class FirebaseUpdateThreadArticle(private val context: Context) : Thread() {
    private val databaseReference: DatabaseReference

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.User)
    }

    override fun run() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    for (likedArticleSnapshot in dataSnapshot.child(Const.Userid)
                        .child(Const.likedarticle).children) {
                        val likedarticle = likedArticleSnapshot.getValue(
                            String::class.java
                        )
                        addStringToList2(likedarticle!!)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Load Data Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}