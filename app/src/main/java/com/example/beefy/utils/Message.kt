package com.example.beefy.utils

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    val id:String? = null,
    val text: String? = null,
    val senderId: String? = null,
    val receiverId: String? = null,
    val timestamp: Long? = null
)

