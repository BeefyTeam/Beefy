package com.example.beefy.ui.auth.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.source.local.UserPreference
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val userPreference: UserPreference):ViewModel() {

    fun saveToken(token:String){
        viewModelScope.launch {
            userPreference.saveToken(token)
        }
    }

}