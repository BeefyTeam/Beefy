package com.example.beefy.ui.seller.selleradditemscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.AddProductResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class SellerAddItemViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository) : ViewModel() {

    private var _add = MutableLiveData<Resource<AddProductResponse>>()
    val add: LiveData<Resource<AddProductResponse>> = _add

    fun addProduct(
        namaBarang: RequestBody,
        deskripsi: RequestBody,
        harga: RequestBody,
        fileImage: MultipartBody.Part
    ) {
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                sellerRepository.addProduct(it.toRequestBody("text/plain".toMediaType()), namaBarang, deskripsi, harga, fileImage).collect{
                    _add.postValue(it)
                }
            }
        }

    }

}