package com.example.beefy.ui.auth.registersellerscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.repository.SellerRepository
import com.example.beefy.data.response.EditPPPenjualResponse
import com.example.beefy.data.response.EditPenjualResponse
import com.example.beefy.data.response.RegisterPenjualResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterSellerViewModel(private val authRepository: AuthRepository, private val sellerRepository: SellerRepository): ViewModel() {

    private var _registerPenjual = MutableLiveData<Resource<RegisterPenjualResponse>>()
    val registerPenjual : LiveData<Resource<RegisterPenjualResponse>> = _registerPenjual

    private var _registerAddPPPenjual = MutableLiveData<Resource<EditPPPenjualResponse>>()
    val registerAddPPPenjual : LiveData<Resource<EditPPPenjualResponse>> = _registerAddPPPenjual

    private var _registerEditPenjual = MutableLiveData<Resource<EditPenjualResponse>>()
    val registerEditPenjual : LiveData<Resource<EditPenjualResponse>> = _registerEditPenjual

    fun registerPenjual(namaToko : String, nomorTelepon:String, email:String, password:String) {
        viewModelScope.launch {
            authRepository.registerPenjual(namaToko, nomorTelepon, email, password).collect {
                _registerPenjual.postValue(it)
            }
        }
    }

    fun editPPPenjual(idToko: RequestBody, fileImage : MultipartBody.Part,) {
        viewModelScope.launch {
            sellerRepository.addPPPenjual(idToko, fileImage).collect{
                _registerAddPPPenjual.postValue(it)
            }
        }
    }

    fun editPenjual(idToko: String, alamatLengkap:String, jamBuka:String, jamTutup:String, metodePembayaran:String, rekening:String) {
        viewModelScope.launch {
            sellerRepository.addDetailPenjual(idToko, alamatLengkap, jamBuka, jamTutup, metodePembayaran, rekening).collect{
                _registerEditPenjual.postValue(it)
            }
        }
    }


}