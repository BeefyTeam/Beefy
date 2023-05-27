package com.example.beefy.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference(private val dataStore: DataStore<Preferences>) {

    private val ID = stringPreferencesKey("id")
    private val NAME = stringPreferencesKey("name")
    private val EMAIL = stringPreferencesKey("email")
    private val TOKEN_KEY = stringPreferencesKey("token")

    fun getId(): Flow<String> {
        return dataStore.data.map {
            it[ID] ?: ""
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map {
            it[NAME] ?: ""
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.map {
            it[EMAIL] ?: ""
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }

    suspend fun saveId(id: String) {
        dataStore.edit {
            it[ID] = id
        }
    }

    suspend fun saveName(name: String) {
        dataStore.edit {
            it[NAME] = name
        }
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit {
            it[EMAIL] = email
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

}