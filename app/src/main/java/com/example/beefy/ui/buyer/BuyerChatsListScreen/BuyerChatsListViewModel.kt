package com.example.beefy.ui.buyer.BuyerChatsListScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuyerChatsListViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository): ViewModel() {

    private var _sellerChatList = MutableLiveData<Resource<List<DetailPenjualResponse>>>()
    var sellerChatList : LiveData<Resource<List<DetailPenjualResponse>>> = _sellerChatList


    fun getUserId() = userPrefRepository.getUserId().asLiveData()


    fun getSellerChatList(listAkunIdToko : List<String>){

        viewModelScope.launch {
                sellerRepository.getDetailPenjualByIdAccount(listAkunIdToko).collect{
                    _sellerChatList.postValue(it)
                }
        }


    }



}