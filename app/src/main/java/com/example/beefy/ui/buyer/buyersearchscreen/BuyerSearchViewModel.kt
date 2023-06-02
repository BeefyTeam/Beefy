package com.example.beefy.ui.buyer.buyersearchscreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beefy.data.repository.BuyerRepository

class BuyerSearchViewModel(private val buyerRepository: BuyerRepository):ViewModel() {
    private var _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() =  _searchQuery

    fun setQuery(query:String){
        _searchQuery.value = query

    }

}