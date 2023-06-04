package com.example.beefy.ui.seller.sellertransactionscreen.sellercompletetransactionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.SellerOrderProductResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class SellerCompleteTransactionViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository): ViewModel() {

    private var _orderInComplete = MutableLiveData<Resource<List<SellerOrderProductResponse>>>()
    val orderInComplete : LiveData<Resource<List<SellerOrderProductResponse>>> = _orderInComplete

    init {
        getOrderInComplete()
    }

    fun getOrderInComplete(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if(it.isNotEmpty()){
                    sellerRepository.sellerGetOrderInComplete(it.toInt()).collect{
                        _orderInComplete.postValue(it)
                    }
                }

            }
        }
    }

}