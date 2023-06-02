package com.example.beefy.ui.seller.selleredititemscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.response.EditProductResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SellerEditItemViewModel(private val sellerRepository: SellerRepository): ViewModel() {

    private var _edit = MutableLiveData<Resource<EditProductResponse>>()
    val edit : LiveData<Resource<EditProductResponse>> = _edit

    fun editProduct(idItem : RequestBody, namaBarang : RequestBody, deskripsi : RequestBody, harga : RequestBody, fileImage: MultipartBody.Part){
        viewModelScope.launch {
            sellerRepository.editProduct(idItem, namaBarang, deskripsi, harga, fileImage).collect{
                _edit.postValue(it)
            }
        }
    }

}