package com.example.beefy.ui.seller.sellerprofilescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class SellerProfileViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository): ViewModel() {
    private var _userProfile = MutableLiveData<Resource<DetailPenjualResponse>>()
    val userProfile : LiveData<Resource<DetailPenjualResponse>> = _userProfile


    fun getDetailPenjual(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if(it.isNotEmpty()){
                    sellerRepository.getDetailPenjual(it.toInt()).collect{
                        _userProfile.postValue(it)
                    }
                }
            }
        }
    }

    fun clearPref()  {
        viewModelScope.launch {
            userPrefRepository.clearPref()
        }
    }
}