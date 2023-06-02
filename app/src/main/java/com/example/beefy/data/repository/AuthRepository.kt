package com.example.beefy.data.repository

import android.util.Log

import com.example.beefy.data.response.ErrorResponse
import com.example.beefy.data.response.LoginResponse
import com.example.beefy.data.response.RefreshTokenResponse
import com.example.beefy.data.response.RegisterBuyerResponse
import com.example.beefy.data.response.RegisterPenjualResponse
import com.example.beefy.data.source.remote.ApiServices
import com.example.beefy.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class AuthRepository(
    private val apiServices: ApiServices,
) {

    suspend fun login(
        email: String,
        password: String
    ) : Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.login(email, password)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("AuthRepository", "login HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun refreshToken(
        tokenRefresh : String
    ) : Flow<Resource<RefreshTokenResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.refreshToken(tokenRefresh)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("AuthRepository", "refresh token HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    //register penjual
    suspend fun registerPenjual(
        namaToko : String,
        nomorTelepon:String,
        email:String,
        password:String
    ) : Flow<Resource<RegisterPenjualResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.registerPenjual("Bearer DAFTAR", namaToko, nomorTelepon, email, password)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("AuthRepository", "register penjual HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    //register pembeli
    suspend fun registerBuyer(
        name : String,
        email:String,
        password:String
    ) : Flow<Resource<RegisterBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.registerPembeli("Bearer DAFTAR", name, email, password)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("AuthRepository", "register pembeli HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    private fun getError(e: HttpException): String {
        val errorMessage = e.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorMessage, ErrorResponse::class.java)
        return errorResponse.message.toString()
    }

}