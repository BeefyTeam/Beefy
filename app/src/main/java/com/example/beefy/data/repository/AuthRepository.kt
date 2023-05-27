package com.example.beefy.data.repository

import com.example.beefy.data.source.local.UserPreference
import com.example.beefy.data.source.remote.ApiServices

class AuthRepository(
    private val apiServices: ApiServices,
    private val userPreference: UserPreference
) {

    fun getToken() = userPreference.getToken()

    suspend fun clearPref() = userPreference.clear()

}