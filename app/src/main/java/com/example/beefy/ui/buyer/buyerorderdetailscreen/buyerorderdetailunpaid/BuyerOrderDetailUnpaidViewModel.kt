package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailunpaid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.data.response.UploadPaymentProofResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BuyerOrderDetailUnpaidViewModel(private val buyerRepository: BuyerRepository, private val sellerRepository: SellerRepository) : ViewModel() {

    private var _orderDetail = MutableLiveData<Resource<DetailOrderResponse>>()
    val orderDetail : LiveData<Resource<DetailOrderResponse>> = _orderDetail

    private var _sellerDetail = MutableLiveData<Resource<DetailPenjualResponse>>()
    val sellerDetail : LiveData<Resource<DetailPenjualResponse>> = _sellerDetail

    private var _uploadPaymentProof = MutableLiveData<Resource<UploadPaymentProofResponse>>()
    val uploadPaymentProof : LiveData<Resource<UploadPaymentProofResponse>> = _uploadPaymentProof

    fun getOrderDetail(idOrder:Int){
        viewModelScope.launch {
            buyerRepository.buyerGetDetailOrder(idOrder).collect{
                _orderDetail.postValue(it)
            }
        }
    }

    fun getSellerDetail(idPenjual : Int){
        viewModelScope.launch {
            sellerRepository.getDetailPenjual(idPenjual).collect{
                _sellerDetail.postValue(it)
            }
        }
    }

    fun uploadPaymentProof(idOrder: RequestBody, image : MultipartBody.Part){
        viewModelScope.launch {
            buyerRepository.uploadPaymentProof(idOrder, image).collect{
                _uploadPaymentProof.postValue(it)
            }
        }
    }

}