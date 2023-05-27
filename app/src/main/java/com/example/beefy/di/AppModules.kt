package com.example.beefy.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.source.local.UserPreference
import com.example.beefy.data.source.remote.ApiConfig
import com.example.beefy.data.source.remote.ApiServices
import com.example.beefy.ui.auth.loginscreen.LoginScreenViewModel
import com.example.beefy.ui.auth.splashscreen.SplashScreenViewModel
import com.example.beefy.ui.buyer.buyerprofilescreen.BuyerProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val Context.dataStore by preferencesDataStore(name = "user_preferences")


val apiModule = module {
    single { ApiConfig(androidContext().dataStore).getApiService() }
}

val userPreferenceModule = module {
    single { UserPreference(androidContext().dataStore) }
}

val repositoryModule = module {
    single { AuthRepository(get(),get()) }

}

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get()) }

    viewModel { LoginScreenViewModel(get()) }

    viewModel { BuyerProfileViewModel(get()) }

}