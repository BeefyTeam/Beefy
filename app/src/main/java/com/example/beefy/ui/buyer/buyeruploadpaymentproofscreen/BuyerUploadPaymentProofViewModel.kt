package com.example.beefy.ui.buyer.buyeruploadpaymentproofscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.response.UploadPaymentProofResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class BuyerUploadPaymentProofViewModel(private val buyerRepository: BuyerRepository):ViewModel() {

    private var _uploadPaymentResponse = MutableLiveData<Resource<UploadPaymentProofResponse>>()
    val uploadPaymentProofResponse : LiveData<Resource<UploadPaymentProofResponse>> = _uploadPaymentResponse

    fun uploadPayment(
        idOrder:RequestBody,
        file_image: MultipartBody.Part
    ){
        viewModelScope.launch {
            buyerRepository.uploadPaymentProof(idOrder,file_image).collect{
                _uploadPaymentResponse.postValue(it)
            }
        }
    }

}