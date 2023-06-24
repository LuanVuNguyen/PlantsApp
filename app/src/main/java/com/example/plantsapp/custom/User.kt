package com.example.plantsapp.custom

import com.google.firebase.database.connection.util.StringListReader

data class User(
    val displayName: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    val likedPlants: StringListReader? = null,
    val likedArticles: StringListReader? = null
)