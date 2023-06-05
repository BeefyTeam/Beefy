package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuspaid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.PaidOrderResponse
import com.example.beefy.data.response.UnpaidOrderResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class BuyerOrderStatusPaidViewModel(private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository):
    ViewModel(){

    private var _orderList = MutableLiveData<Resource<List<PaidOrderResponse>>>()
    val orderList : LiveData<Resource<List<PaidOrderResponse>>> = _orderList

    init {
        getOrderList()
    }

    fun getOrderList(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if(!it.isNullOrBlank()){
                    buyerRepository.buyerGetPaidOrder(it.toInt()).collect{
                        _orderList.postValue(it)
                    }
                }
            }
        }
    }
}