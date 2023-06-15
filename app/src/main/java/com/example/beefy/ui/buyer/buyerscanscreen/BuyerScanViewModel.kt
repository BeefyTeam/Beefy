package com.example.beefy.ui.buyer.buyerscanscreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.MLRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.MlScanMeatResponse
import com.example.beefy.data.response.SaveScanResponse
import com.example.beefy.data.response.ScanMeatResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class BuyerScanViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val buyerRepository: BuyerRepository,
    private val mlRepository: MLRepository
) : ViewModel() {

    lateinit var token: String

    private var _scanResult = MutableLiveData<Resource<MlScanMeatResponse>>()
    val scanResult: LiveData<Resource<MlScanMeatResponse>> = _scanResult

    private var _savescanResult = MutableLiveData<Resource<SaveScanResponse>>()
    val savescanResulttt: LiveData<Resource<SaveScanResponse>> = _savescanResult

    init {
        getIdType()
    }

    fun getIdType() {
        viewModelScope.launch {
            userPrefRepository.getIdType().collect {
                token = it
            }
        }
    }


    fun scanMeat(
        image: MultipartBody.Part
    ) {
        viewModelScope.launch {
            mlRepository.MLscanMeat(image).collect { result ->
                _scanResult.postValue(result)
            }
        }

    }

    fun saveScanResult(
        label: String,
        levelKesegaran: String,
        type: String,
        image: MultipartBody.Part
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(TAG, "saveScanResult: $type")
                buyerRepository.saveScanResult(
                    token.toRequestBody("text/plain".toMediaType()),
                    label.toRequestBody("text/plain".toMediaType()),
                    levelKesegaran.toRequestBody("text/plain".toMediaType()),
                    type.toRequestBody("text/plain".toMediaType()),
                    image
                ).collect()
        }
    }
}

