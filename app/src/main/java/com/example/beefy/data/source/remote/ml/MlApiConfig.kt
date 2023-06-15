package com.example.beefy.data.source.remote.ml

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.beefy.data.source.remote.ApiServices
import com.example.beefy.data.source.remote.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MlApiConfig(private val dataStore: DataStore<Preferences>) {

    fun getMLApiService(): MlApiServices {

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // Timeout untuk menghubungkan ke server
            .readTimeout(30, TimeUnit.SECONDS) // Timeout untuk membaca respon dari server
            .writeTimeout(30, TimeUnit.SECONDS) // Timeout untuk menulis permintaan ke server
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(dataStore))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://beefy-ml-b52v2foiya-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        return retrofit.create(MlApiServices::class.java)
    }

}