package com.example.beefy.ui.buyer.buyerscanscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.ScanMeatResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class BuyerScanViewModel (private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository): ViewModel() {

    private var _scanResult = MutableLiveData<Resource<ScanMeatResponse>>()
    val scanResult : LiveData<Resource<ScanMeatResponse>> = _scanResult

    fun scanMeat(
        image : MultipartBody.Part
    ){

        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                if(!it.isNullOrEmpty()){
                    buyerRepository.scanMeat(it.toRequestBody("text/plain".toMediaType()), image).collect{
                        _scanResult.postValue(it)
                    }
                }
            }
        }
    }

}