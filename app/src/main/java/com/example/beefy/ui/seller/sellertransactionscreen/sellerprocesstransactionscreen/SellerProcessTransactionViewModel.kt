package com.example.beefy.ui.seller.sellertransactionscreen.sellerprocesstransactionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.SellerOrderProductResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class SellerProcessTransactionViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository): ViewModel() {

    private var _orderInProcess = MutableLiveData<Resource<List<SellerOrderProductResponse>>>()
    val orderInProcess : LiveData<Resource<List<SellerOrderProductResponse>>> = _orderInProcess

    init {
        getOrderInWaiting()
    }

    fun getOrderInWaiting(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if(it.isNotEmpty()){
                    sellerRepository.sellerGetOrderInProcess(it.toInt()).collect{
                        _orderInProcess.postValue(it)
                    }
                }

            }
        }
    }

}