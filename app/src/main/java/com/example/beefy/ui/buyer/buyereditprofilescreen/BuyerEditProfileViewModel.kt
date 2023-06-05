package com.example.beefy.ui.buyer.buyereditprofilescreen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.data.response.EditBuyerResponse
import com.example.beefy.data.response.EditPPBuyerResponse
import com.example.beefy.data.response.EditPPPenjualResponse
import com.example.beefy.data.response.EditPenjualResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class BuyerEditProfileViewModel(private val userPrefRepository: UserPrefRepository, private val buyerRepository: BuyerRepository):ViewModel(){

    private var idPembeli: String = ""

    private var _userProfile = MutableLiveData<Resource<DetailBuyerResponse>>()
    val userProfile: LiveData<Resource<DetailBuyerResponse>> = _userProfile

    private var _buyerEditPPPenjual = MutableLiveData<Resource<EditPPBuyerResponse>>()
    val buyerEditPPPenjual: LiveData<Resource<EditPPBuyerResponse>> = _buyerEditPPPenjual

    private var _buyerEditPenjual = MutableLiveData<Resource<EditBuyerResponse>>()
    val buyerEditPenjual: LiveData<Resource<EditBuyerResponse>> = _buyerEditPenjual

    fun getUserProfile(){
        viewModelScope.launch {
            userPrefRepository.getIdType().collect{
                idPembeli = it
                if(!it.isNullOrEmpty()){
                    buyerRepository.getDetailBuyer(it.toInt()).collect{
                        _userProfile.postValue(it)
                    }
                }
            }
        }
    }

    fun editPPBuyer(image: MultipartBody.Part) {
        viewModelScope.launch {
            buyerRepository.editPPBuyer(idPembeli.toRequestBody("text/plain".toMediaType()), image)
                .collect {
                    _buyerEditPPPenjual.value = it
                }

        }
    }

    fun editBuyer(
        alamatLengkap: String,
        namaPenerima: String,
        nomorTelepon: String,
        labelAlamat: String,
        nama : String
    ) {
        viewModelScope.launch {
            buyerRepository.editDetailBuyer(
                idPembeli, alamatLengkap, namaPenerima, nomorTelepon, labelAlamat, nama
            ).collect {
                _buyerEditPenjual.value = it
            }

        }
    }

    fun confirmEditProfile(
        image: MultipartBody.Part,
        alamatLengkap: String,
        namaPenerima: String,
        nomorTelepon: String,
        labelAlamat: String,
        nama : String
    ) {
        viewModelScope.launch {
            val editPPJob = async { editPPBuyer(image) }
            val editBuyerJob = async { editBuyer(alamatLengkap, namaPenerima, nomorTelepon, labelAlamat, nama) }

            val editPPResult = editPPJob.await()
            val editBuyerResult = editBuyerJob.await()



        }

    }

}