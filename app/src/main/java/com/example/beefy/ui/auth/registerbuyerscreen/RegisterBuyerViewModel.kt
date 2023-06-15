package com.example.beefy.ui.auth.registerbuyerscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.repository.BuyerRepository
import com.example.beefy.data.response.EditBuyerResponse
import com.example.beefy.data.response.EditPPBuyerResponse
import com.example.beefy.data.response.RegisterBuyerResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterBuyerViewModel(
    private val authRepository: AuthRepository,
    private val buyerRepository: BuyerRepository
) : ViewModel() {

    private var _registerBuyer = MutableLiveData<Resource<RegisterBuyerResponse>>()
    val registerBuyer: LiveData<Resource<RegisterBuyerResponse>> = _registerBuyer

    private var _registerPPBuyer = MutableLiveData<Resource<EditPPBuyerResponse>>()
    val registerPPBuyer: LiveData<Resource<EditPPBuyerResponse>> = _registerPPBuyer

    private var _registerEditBuyer = MutableLiveData<Resource<EditBuyerResponse>>()
    val registerEditBuyer: LiveData<Resource<EditBuyerResponse>> = _registerEditBuyer

    fun registerBuyer(name: String, email: String, password: String) {
        viewModelScope.launch {
            authRepository.registerBuyer(name, email, password).collect {
                _registerBuyer.postValue(it)
            }
        }
    }

    fun registerPPBuyer(idBuyer: RequestBody, fileImage: MultipartBody.Part) {
        viewModelScope.launch {
            buyerRepository.addPPBuyer(idBuyer, fileImage).collect {
                _registerPPBuyer.postValue(it)
            }
        }
    }

    fun registerEditBuyer(
        idPembeli: String,
        alamatLengkap: String,
        namaPenerima: String,
        nomorTelepon: String,
        labelAlamat: String,
        nama: String
    ) {
        viewModelScope.launch {
            buyerRepository.addDetailBuyer(
                idPembeli,
                alamatLengkap,
                namaPenerima,
                nomorTelepon,
                labelAlamat,
                nama
            ).collect {
                _registerEditBuyer.postValue(it)
            }
        }
    }


}