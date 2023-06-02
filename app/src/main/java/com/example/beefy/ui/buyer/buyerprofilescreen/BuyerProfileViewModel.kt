package com.example.beefy.ui.buyer.buyerprofilescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.repository.UserPrefRepository
import kotlinx.coroutines.launch

class BuyerProfileViewModel(private val userPrefRepository: UserPrefRepository):ViewModel() {
    fun clearPref(){
        viewModelScope.launch {
            userPrefRepository.clearPref()
        }
    }
}