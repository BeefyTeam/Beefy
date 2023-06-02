package com.example.beefy.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {

    private val USER_ID = stringPreferencesKey("ID")
    private val USER_TYPE = stringPreferencesKey("userType")
    private val ID_TYPE = stringPreferencesKey("idType")
    private val TOKEN_ACCESS_KEY = stringPreferencesKey("tokenAccess")
    private val TOKEN_REFRESH_KEY = stringPreferencesKey("tokenRefresh")

    fun getUserId(): Flow<String> {
        return dataStore.data.map {
            it[USER_ID] ?: ""
        }
    }

    fun getUserType(): Flow<String> {
        return dataStore.data.map {
            it[USER_TYPE] ?: ""
        }
    }

    fun getIdType(): Flow<String> {
        return dataStore.data.map {
            it[ID_TYPE] ?: ""
        }
    }
    fun getTokenAccess(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_ACCESS_KEY] ?: ""
        }
    }

    fun getTokenRefresh(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_REFRESH_KEY] ?: ""
        }
    }

    suspend fun saveTokenAccess(tokenAccess: String){
        dataStore.edit {
            it[TOKEN_ACCESS_KEY] = tokenAccess
        }
    }

    suspend fun saveUserPref(user : UserPreferenceClass){
        dataStore.edit {
            it[USER_ID] = user.userId
            it[USER_TYPE] = user.userType
            it[ID_TYPE] = user.idType
            it[TOKEN_ACCESS_KEY] = user.tokenAccess
            it[TOKEN_REFRESH_KEY] = user.tokenRefresh
        }
    }
    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

}

data class UserPreferenceClass(val userId : String, val userType : String, val idType:String, val tokenAccess : String, val tokenRefresh : String)