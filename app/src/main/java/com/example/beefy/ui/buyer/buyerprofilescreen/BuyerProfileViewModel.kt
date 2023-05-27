package com.example.beefy.ui.buyer.buyerprofilescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.AuthRepository
import kotlinx.coroutines.launch

class BuyerProfileViewModel(private val authRepository: AuthRepository):ViewModel() {
    fun clearPref(){
        viewModelScope.launch {
            authRepository.clearPref()
        }
    }
}