package com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailcompletetransactionscreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class SellerDetailCompleteTransactionViewModel(private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository, private val sellerRepository: SellerRepository):
    ViewModel() {

    private var _buyerDetail = MutableLiveData<Resource<DetailBuyerResponse>>()
    val buyerDetail : LiveData<Resource<DetailBuyerResponse>> = _buyerDetail

    private var _detailOrder = MutableLiveData<Resource<DetailOrderResponse>>()
    val detailOrder : LiveData<Resource<DetailOrderResponse>> = _detailOrder

    private var _userId = MutableLiveData<String>()
    val userId : LiveData<String> = _userId

    init {
        getMyId()
    }

    fun getMyId(){
        viewModelScope.launch {
            userPrefRepository.getUserId().collect{
                _userId.value = it
                Log.e(ContentValues.TAG, "getMyId: $it", )
            }
        }
    }

    fun getBuyerDetail(idBuyer:Int){
        viewModelScope.launch {
            buyerRepository.getDetailBuyer(idBuyer).collect{
                _buyerDetail.postValue(it)
            }
        }
    }


    fun getDetailOrder(idOrder: Int){
        viewModelScope.launch {
            sellerRepository.sellerGetDetailOrder(idOrder).collect{
                _detailOrder.postValue(it)
            }
        }
    }

}