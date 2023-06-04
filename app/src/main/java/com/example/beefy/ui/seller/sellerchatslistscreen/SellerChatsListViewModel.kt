package com.example.beefy.ui.seller.sellerchatslistscreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SellerChatsListViewModel(private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository):ViewModel() {

    private var _userList = MutableLiveData<List<String>>()
    val userList : LiveData<List<String>> = _userList

    fun getUserId() = userPrefRepository.getUserId().asLiveData()

    fun getUsername(items : List<String>){
        val newList = mutableListOf<String>()
        viewModelScope.launch {
            items.forEach {
                buyerRepository.getDetailBuyer(it.toInt()).collect{
                    when(it){
                        is Resource.Success -> {
                            newList.add(it.data.nama.toString())
                        }

                        is Resource.Loading -> {
                        }

                        is Resource.Error -> {
                            Log.e(TAG, "getUsername: error", )
                        }
                    }
                }
            }
            _userList.postValue(newList)
        }
    }

}