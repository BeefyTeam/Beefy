package com.example.beefy.ui.auth.loginscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beefy.data.repository.AuthRepository
import com.example.beefy.data.repository.UserPrefRepository
import com.example.beefy.data.response.LoginResponse
import com.example.beefy.data.source.local.UserPreferenceClass
import com.example.beefy.utils.Resource
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val authRepository: AuthRepository, private val userPrefRepository: UserPrefRepository):ViewModel() {

    private var _userLogin = MutableLiveData<Resource<LoginResponse>>()
    val userLogin: LiveData<Resource<LoginResponse>> = _userLogin

    fun login(email:String, password:String){
        viewModelScope.launch {
            authRepository.login(email, password).collect{
                _userLogin.postValue(it)
            }
        }
    }

    fun saveUserPref(userId: String, userType:String, idType:String, tokenAccess:String, tokenRefresh: String){

        val userPref = UserPreferenceClass(userId, userType, idType, tokenAccess, tokenRefresh)

        viewModelScope.launch {
            userPrefRepository.saveUserPref(userPref)
        }
    }

}