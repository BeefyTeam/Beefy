package com.example.beefy.data.repository

import android.util.Log
import com.example.beefy.data.response.EditBuyerResponse
import com.example.beefy.data.response.EditPPBuyerResponse
import com.example.beefy.data.response.EditPPPenjualResponse
import com.example.beefy.data.response.EditPenjualResponse
import com.example.beefy.data.response.ErrorResponse
import com.example.beefy.data.response.HelloWorldResponse
import com.example.beefy.data.source.remote.ApiServices
import com.example.beefy.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class BuyerRepository(
    private val apiServices: ApiServices
) {

    suspend fun editPPBuyer(
        idBuyer: RequestBody,
        fileImage : MultipartBody.Part,
    ) : Flow<Resource<EditPPBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editPPBuyer("Bearer DAFTAR", idBuyer, fileImage)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("BuyerRepository", "edit PP buyer HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun editBuyer(
        idPembeli:String,
        alamatLengkap:String,
        namaPenerima:String,
        nomorTelepon:String,
        labelAlamat:String,
        nama:String
    ) : Flow<Resource<EditBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editBuyer("Bearer DAFTAR", idPembeli, alamatLengkap, namaPenerima, nomorTelepon, labelAlamat, nama)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("BuyerRepository", "edit pembeli HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    fun helloWorld(): Flow<Resource<HelloWorldResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.helloWorld()
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "helloworld HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun getError(e: HttpException): String {
        val errorMessage = e.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorMessage, ErrorResponse::class.java)
        return errorResponse.message.toString()
    }



}