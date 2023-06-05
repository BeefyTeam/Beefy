package com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailwaitingtransactionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.response.AcceptOrderResponse
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SellerDetailWaitingTransactionViewModel(private val buyerRepository: BuyerRepository, val sellerRepository: SellerRepository):ViewModel() {
    private var _buyerDetail = MutableLiveData<Resource<DetailBuyerResponse>>()
    val buyerDetail : LiveData<Resource<DetailBuyerResponse>> = _buyerDetail

    private var _acceptOrder = MutableLiveData<Resource<AcceptOrderResponse>>()
    val acceptOrder : LiveData<Resource<AcceptOrderResponse>> = _acceptOrder

    private var _detailOrder = MutableLiveData<Resource<DetailOrderResponse>>()
    val detailOrder : LiveData<Resource<DetailOrderResponse>> = _detailOrder

    fun getBuyerDetail(idBuyer:Int){
        viewModelScope.launch {
            buyerRepository.getDetailBuyer(idBuyer).collect{
                _buyerDetail.postValue(it)
            }
        }
    }

    fun acceptOrder(idOrder: Int){
        viewModelScope.launch {
            sellerRepository.sellerAcceptOrder(idOrder).collect{
                _acceptOrder.postValue(it)
            }
        }
    }

    fun getDetailOrder(idOrder: Int){
        viewModelScope.launch {
            sellerRepository.getDetailOrder(idOrder).collect{
                _detailOrder.postValue(it)
            }
        }
    }

}