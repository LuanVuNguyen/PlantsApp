package com.example.plantsapp.custom

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object DataHelper {
    fun getAlphabetFullData(callback: (ArrayList<String>) -> Unit) {
        val dataList = ArrayList<String>()

        // Assume "firebaseRef" is your DatabaseReference pointing to the root node of your Firebase database
        val firebaseRef = FirebaseDatabase.getInstance().reference
        val parentNode = Const.refPlant

        firebaseRef.child(parentNode).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val childNode = childSnapshot.key.toString()
                    dataList.add(childNode)
                }
                callback(dataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {


            }
        })
    }
}
