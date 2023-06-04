package com.example.beefy.ui.buyer.buyersearchscreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.SearchStoreResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class BuyerSearchViewModel(private val buyerRepository: BuyerRepository):ViewModel() {
    private var _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() =  _searchQuery

    private var _productResult = MutableLiveData<Resource<List<Product>>>()
    val productResult : LiveData<Resource<List<Product>>> = _productResult

    private var _storeResult = MutableLiveData<Resource<List<SearchStoreResponse>>>()
    val storeResult : LiveData<Resource<List<SearchStoreResponse>>> = _storeResult

    fun setQuery(query:String){
        _searchQuery.value = query
    }

    fun searchProduct(productName : String){
        viewModelScope.launch {
            buyerRepository.searchProduct(productName).collect{
                _productResult.postValue(it)
            }
        }
    }

    fun searchStore(storeName : String){
        viewModelScope.launch {
            buyerRepository.searchStore(storeName).collect{
                _storeResult.postValue(it)
            }
        }
    }

}