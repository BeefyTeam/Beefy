package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuswaitingpayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.BuyerOrderProductResponse
import com.example.beefy.data.response.UnpaidOrderResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class BuyerOrderStatusUnpaidViewModel(private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository):ViewModel() {

    private var _orderList = MutableLiveData<Resource<List<UnpaidOrderResponse>>>()
    val orderList : LiveData<Resource<List<UnpaidOrderResponse>>> = _orderList

    init {
        getOrderList()
    }

    fun getOrderList(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if(!it.isNullOrBlank()){
                    buyerRepository.buyerGetUnpaidOrder(it.toInt()).collect{
                        _orderList.postValue(it)
                    }
                }
            }
        }
    }

}