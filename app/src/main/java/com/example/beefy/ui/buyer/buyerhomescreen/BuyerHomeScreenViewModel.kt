package com.example.beefy.ui.buyer.buyerhomescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.TrendingStoreResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuyerHomeScreenViewModel(private val buyerRepository: BuyerRepository): ViewModel() {

    private var _trendingStore = MutableLiveData<Resource<List<TrendingStoreResponse>>>()
    val trendingStore : LiveData<Resource<List<TrendingStoreResponse>>> = _trendingStore

    private var _trendingProduct = MutableLiveData<Resource<List<Product>>>()
    val trendingProduct : LiveData<Resource<List<Product>>> = _trendingProduct


    init {
        getTrendingProduct()
        getTrendingStore()
    }

    fun getTrendingStore(){
        viewModelScope.launch {
            buyerRepository.getTrendingStore().collect{
                _trendingStore.postValue(it)
            }
        }
    }

    fun getTrendingProduct(){
        viewModelScope.launch {
            buyerRepository.getTrendingProduct().collect{
                _trendingProduct.postValue(it)
            }
        }
    }



}