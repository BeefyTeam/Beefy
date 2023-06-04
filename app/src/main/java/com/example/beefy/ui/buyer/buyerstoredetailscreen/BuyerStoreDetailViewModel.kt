package com.example.beefy.ui.buyer.buyerstoredetailscreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.data.response.Product
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuyerStoreDetailViewModel(private val userPrefRepository: UserPrefRepository, private val sellerRepository: SellerRepository): ViewModel() {

    private var _userId = MutableLiveData<String>()
    val userId : LiveData<String> = _userId

    private var _userDetail = MutableLiveData<Resource<DetailPenjualResponse>>()
    val userDetail : LiveData<Resource<DetailPenjualResponse>> = _userDetail

    private var _productList = MutableLiveData<Resource<List<Product>>>()
    val productList : LiveData<Resource<List<Product>>> = _productList


    init {
        getMyId()
    }

    fun getUserDetail(idToko:String){
        viewModelScope.launch {
            sellerRepository.getDetailPenjual(idToko.toInt()).collect{
                _userDetail.postValue(it)
            }
        }
    }

    fun getProductList(idToko: String){
        viewModelScope.launch{
            sellerRepository.getProducts(idToko.toInt()).collect{
                _productList.postValue(it)
            }
        }
    }

    fun getMyId(){
        viewModelScope.launch {
            userPrefRepository.getUserId().collect{
                _userId.value = it
                Log.e(ContentValues.TAG, "getMyId: $it", )
            }
        }
    }

}