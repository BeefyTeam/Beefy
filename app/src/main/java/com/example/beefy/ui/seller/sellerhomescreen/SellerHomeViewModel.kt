package com.example.beefy.ui.seller.sellerhomescreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.Product
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SellerHomeViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository): ViewModel() {


    private var _products = MutableLiveData<Resource<List<Product>>>()
    val product : LiveData<Resource<List<Product>>> = _products

    init {
        getProducts()
    }

    private fun getProducts(){
        viewModelScope.launch{
            userPrefRepository.getIdType().collect{
                if(!it.isNullOrBlank()){
                    sellerRepository.getProducts(it.toInt()).collect{
                        _products.postValue(it)
                    }
                }
            }
        }
    }


}