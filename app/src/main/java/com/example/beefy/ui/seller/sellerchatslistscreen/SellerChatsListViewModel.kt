package com.example.beefy.ui.seller.sellerchatslistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.beefy.data.repository.UserPrefRepository

class SellerChatsListViewModel(private val userPrefRepository: UserPrefRepository):ViewModel() {

    fun getUserId() = userPrefRepository.getUserId().asLiveData()

}