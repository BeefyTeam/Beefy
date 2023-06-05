package com.example.beefy.ui.buyer.buyerproductdetailscreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuyerProductDetailViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository):ViewModel() {

    private var _userId = MutableLiveData<String>()
    val userId : LiveData<String> = _userId

    private var _sellerProfile = MutableLiveData<Resource<DetailPenjualResponse>>()
    val sellerProfile : LiveData<Resource<DetailPenjualResponse>> = _sellerProfile

    init {
        getMyId()
    }

    fun getSellerProfile(idToko : String){
        viewModelScope.launch {
            sellerRepository.getDetailPenjual(idToko.toInt()).collect{
                _sellerProfile.postValue(it)
            }
        }
    }

    fun getMyId(){
        viewModelScope.launch {
            userPrefRepository.getUserId().collect{
                _userId.value = it
            }
        }
    }


}