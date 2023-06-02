package com.example.beefy.ui.buyer.BuyerChatsListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.beefy.data.repository.UserPrefRepository

class BuyerChatsListViewModel(private val userPrefRepository: UserPrefRepository): ViewModel() {

    fun getUserId() = userPrefRepository.getUserId().asLiveData()

}