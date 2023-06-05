package com.example.beefy.ui.auth.forgotpasswordscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.response.ForgotPasswordResponse
import com.example.beefy.data.response.LoginResponse
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val authRepository: AuthRepository): ViewModel() {

    private var _forgetPassword = MutableLiveData<Resource<ForgotPasswordResponse>>()
    val forgetPassword: LiveData<Resource<ForgotPasswordResponse>> = _forgetPassword

    fun forgetPassword(email:String, newPassword:String){
        viewModelScope.launch {
            authRepository.forgetPassword(email, newPassword).collect{
                _forgetPassword.postValue(it)
            }
        }
    }

}