package com.example.beefy.data.repository

import com.example.beefy.data.source.local.UserPreference
import com.example.beefy.data.source.local.UserPreferenceClass

class UserPrefRepository(
    private val userPreference: UserPreference
) {
    fun getUserId() = userPreference.getUserId()
    fun getUserType() = userPreference.getUserType()
    fun getIdType() = userPreference.getIdType()
    fun getTokenAccess() = userPreference.getTokenAccess()
    fun getTokenRefresh() = userPreference.getTokenRefresh()

    suspend fun saveTokenAccess(tokenAccess:String) = userPreference.saveTokenAccess(tokenAccess)

    suspend fun saveUserPref(user : UserPreferenceClass) = userPreference.saveUserPref(user)

    suspend fun clearTokenAccess() = userPreference.clearTokenAccess()
    suspend fun clearPref() = userPreference.clear()
}