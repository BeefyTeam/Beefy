package com.example.beefy.ui.seller.sellerchatslistscreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SellerChatsListViewModel(private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository):ViewModel() {

    private var _buyerChatList = MutableLiveData<Resource<List<DetailBuyerResponse>>>()
    var buyerChatList : LiveData<Resource<List<DetailBuyerResponse>>> = _buyerChatList

    fun getUserId() = userPrefRepository.getUserId().asLiveData()

    fun getBuyerChatList(listAkunIdPembeli : List<String>){

        viewModelScope.launch {
            buyerRepository.getDetailPembeliByIdAccount(listAkunIdPembeli).collect{
                _buyerChatList.postValue(it)
            }
        }


    }




}