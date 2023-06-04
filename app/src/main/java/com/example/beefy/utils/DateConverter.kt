package com.example.beefy.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun DateConverter(dateString:String):String {
    val dateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
    return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}