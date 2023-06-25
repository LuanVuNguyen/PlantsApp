package com.example.plantsapp.custom

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FirebaseSignUpHelper {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val mStorage: StorageReference = FirebaseStorage.getInstance().reference

    fun registerUser(
        email: String,
        password: String,
        displayName: String,
        listener: OnRegistrationCompleteListener
    ) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = mAuth.currentUser
                    if (user != null) {
                        val userId: String = user.uid
                        saveUserDetails(userId, displayName)
                        setDefaultAvatar(userId, listener)
                        mDatabase.child("Users").child(userId).child("email").setValue(email)
                        mDatabase.child("Users").child(userId).child("Liked Plants").setValue("")
                        mDatabase.child("Users").child(userId).child("Liked Articles").setValue("")
                    }
                } else {
                    // Xử lý lỗi đăng ký
                    listener.onRegistrationFailed(task.exception?.message)
                }
            }
    }

    private fun saveUserDetails(userId: String, displayName: String) {
        val user = User(displayName = displayName)
        mDatabase.child("Users").child(userId).setValue(user)
    }

    private fun setDefaultAvatar(userId: String, listener: OnRegistrationCompleteListener) {
        val defaultAvatarRef: StorageReference =
            mStorage.child("avatars").child("default_avatar.jpg")
        defaultAvatarRef.downloadUrl.addOnSuccessListener { uri ->
            val avatarUrl: String = uri.toString()
            mDatabase.child("Users").child(userId).child("avatar").setValue(avatarUrl)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        listener.onRegistrationComplete()
                    } else {
                        listener.onRegistrationFailed(task.exception?.message)
                    }
                }
        }.addOnFailureListener { exception ->
            listener.onRegistrationFailed(exception.message)
        }
    }

    interface OnRegistrationCompleteListener {
        fun onRegistrationComplete()
        fun onRegistrationFailed(errorMessage: String?)
    }
}