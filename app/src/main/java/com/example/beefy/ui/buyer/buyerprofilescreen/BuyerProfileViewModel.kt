package com.example.beefy.ui.buyer.buyerprofilescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class BuyerProfileViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val buyerRepository: BuyerRepository
) : ViewModel() {

    private var _userProfile = MutableLiveData<Resource<DetailBuyerResponse>>()
    val userProfile: LiveData<Resource<DetailBuyerResponse>> = _userProfile

    init {
        getUserProfile()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            userPrefRepository.getIdType().collect {
                if (!it.isNullOrEmpty()) {
                    buyerRepository.getDetailBuyer(it.toInt()).collect {
                        _userProfile.postValue(it)
                    }
                }
            }
        }
    }


    fun clearPref() {
        viewModelScope.launch {
            userPrefRepository.clearPref()
        }
    }
}