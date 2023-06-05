package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailcomplete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class BuyerOrderDetailCompleteViewModel(private val buyerRepository: BuyerRepository) : ViewModel()  {
    private var _orderDetail = MutableLiveData<Resource<DetailOrderResponse>>()
    val orderDetail : LiveData<Resource<DetailOrderResponse>> = _orderDetail

    fun getOrderDetail(idOrder:Int){
        viewModelScope.launch {
            buyerRepository.buyerGetDetailOrder(idOrder).collect{
                _orderDetail.postValue(it)
            }
        }
    }
}