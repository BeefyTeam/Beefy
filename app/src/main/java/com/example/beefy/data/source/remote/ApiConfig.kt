package com.example.beefy.data.source.remote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConfig (private val dataStore: DataStore<Preferences>){

    fun getApiService(): ApiServices {

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // Timeout untuk menghubungkan ke server
            .readTimeout(30, TimeUnit.SECONDS) // Timeout untuk membaca respon dari server
            .writeTimeout(30, TimeUnit.SECONDS) // Timeout untuk menulis permintaan ke server
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(dataStore))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://beefy-backend-33n3233q4q-et.a.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        return retrofit.create(ApiServices::class.java)
    }



}