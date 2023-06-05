package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailpaid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.data.response.UploadPaymentProofResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BuyerOrderDetailPaidViewModel(private val buyerRepository: BuyerRepository) : ViewModel() {

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