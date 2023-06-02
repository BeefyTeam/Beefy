package com.example.beefy.ui.buyer.buyerhomescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.response.HelloWorldResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuyerHomeScreenViewModel(private val buyerRepository: BuyerRepository): ViewModel() {

    private var _helloworld = MutableLiveData<Resource<HelloWorldResponse>>()
    val helloworld get() = _helloworld

    fun helloworld(){
        viewModelScope.launch {
            buyerRepository.helloWorld().collect(){
                _helloworld.postValue(it)
            }
        }
    }

}