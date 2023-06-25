package com.example.plantsapp.custom

import android.content.Context
import android.widget.Toast
import com.example.plantsapp.custom.Const.addStringToList
import com.google.firebase.database.*

class FirebaseUpdateThread(context: Context?) : Thread() {
    private val databaseReference: DatabaseReference
    private val context: Context? = null

    init {
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.User)
    }

    override fun run() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    for (likedPlantSnapshot in dataSnapshot.child(Const.Userid)
                        .child(Const.likedplants).children) {
                        val likedPlant = likedPlantSnapshot.getValue(
                            String::class.java
                        )
                        addStringToList(likedPlant!!)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Load Data Failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}