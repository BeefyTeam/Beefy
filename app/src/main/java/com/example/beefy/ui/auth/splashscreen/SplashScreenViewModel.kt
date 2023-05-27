package com.example.beefy.ui.auth.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.source.local.UserPreference
import kotlinx.coroutines.flow.collect

class SplashScreenViewModel(private val authRepository: AuthRepository):ViewModel() {

    fun checkToken() = authRepository.getToken().asLiveData()

}