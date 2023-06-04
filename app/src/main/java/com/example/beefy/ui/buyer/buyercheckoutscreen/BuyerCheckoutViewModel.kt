package com.example.beefy.ui.buyer.buyercheckoutscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.data.response.NewOrderResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuyerCheckoutViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val buyerRepository: BuyerRepository,
    private val sellerRepository: SellerRepository
) : ViewModel() {

    private var _buyerDetail = MutableLiveData<Resource<DetailBuyerResponse>>()
    val buyerDetail: LiveData<Resource<DetailBuyerResponse>> = _buyerDetail

    private var _sellerDetail = MutableLiveData<Resource<DetailPenjualResponse>>()
    val sellerDetail: LiveData<Resource<DetailPenjualResponse>> = _sellerDetail

    private var _orderResponse = MutableLiveData<Resource<NewOrderResponse>>()
    val orderResponse: LiveData<Resource<NewOrderResponse>> = _orderResponse


    fun getBuyerDetail() {
        viewModelScope.launch {
            userPrefRepository.getIdType().collect {
                if (!it.isNullOrEmpty()) {
                    buyerRepository.getDetailBuyer(it.toInt()).collect {
                        _buyerDetail.postValue(it)
                    }
                }
            }
        }
    }

    fun getSellerDetail(idToko: Int) {
        viewModelScope.launch {
            sellerRepository.getDetailPenjual(idToko).collect {
                _sellerDetail.postValue(it)
            }
        }
    }

    fun newOrder(
        idToko: Int,
        idBarang: Int,
        rekening: String,
        catatan: String,
        alamatPengiriman: String,
        metodePembayaran: String,
        biayaPengiriman: Int,
        totalHarga: Int,
        kodeUnik: Int,
        totalItem:Int
    ) {
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{idPembeli->
                buyerRepository.newOrder(idPembeli.toInt(), idToko, idBarang, rekening, catatan, alamatPengiriman, metodePembayaran, biayaPengiriman, totalHarga, kodeUnik, totalItem).collect{
                    _orderResponse.postValue(it)
                }
            }
        }
    }


}