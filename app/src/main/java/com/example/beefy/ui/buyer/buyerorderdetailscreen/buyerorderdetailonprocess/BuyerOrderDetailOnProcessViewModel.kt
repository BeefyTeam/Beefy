package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailonprocess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.response.AcceptOrderResponse
import com.example.beefy.data.response.CompleteOrder
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuyerOrderDetailOnProcessViewModel(private val buyerRepository: BuyerRepository) : ViewModel()  {
    private var _orderDetail = MutableLiveData<Resource<DetailOrderResponse>>()
    val orderDetail : LiveData<Resource<DetailOrderResponse>> = _orderDetail

    private var _finishOrder = MutableLiveData<Resource<CompleteOrder>>()
    val finishOrder : LiveData<Resource<CompleteOrder>> = _finishOrder

    fun getOrderDetail(idOrder:Int){
        viewModelScope.launch {
            buyerRepository.buyerGetDetailOrder(idOrder).collect{
                _orderDetail.postValue(it)
            }
        }
    }

    fun finishOrder(idOrder: Int){
        viewModelScope.launch {
            buyerRepository.finishOrder(idOrder).collect{
                _finishOrder.postValue(it)
            }
        }
    }

}