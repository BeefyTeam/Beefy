package com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailprocesstransactionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class SellerDetailProcessTransactionViewModel(private val buyerRepository: BuyerRepository, private val sellerRepository: SellerRepository): ViewModel() {

    private var _buyerDetail = MutableLiveData<Resource<DetailBuyerResponse>>()
    val buyerDetail : LiveData<Resource<DetailBuyerResponse>> = _buyerDetail

    fun getBuyerDetail(idBuyer:Int){
        viewModelScope.launch {
            buyerRepository.getDetailBuyer(idBuyer).collect{
                _buyerDetail.postValue(it)
            }
        }
    }

}