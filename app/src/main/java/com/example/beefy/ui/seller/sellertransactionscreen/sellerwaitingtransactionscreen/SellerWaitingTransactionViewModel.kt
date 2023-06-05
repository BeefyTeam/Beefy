package com.example.beefy.ui.seller.sellertransactionscreen.sellerwaitingtransactionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.PaidOrderResponse
import com.example.beefy.data.response.SellerOrderProductResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SellerWaitingTransactionViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository): ViewModel() {
    private var _orderInWaiting = MutableLiveData<Resource<List<PaidOrderResponse>>>()
    val orderInWaiting : LiveData<Resource<List<PaidOrderResponse>>> = _orderInWaiting

    init {
        getOrderInWaiting()
    }

    fun getOrderInWaiting(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if(it.isNotEmpty()){
                    sellerRepository.sellerGetPaidOrder(it.toInt()).collect{
                        _orderInWaiting.postValue(it)
                    }
                }
            }
        }
    }
}