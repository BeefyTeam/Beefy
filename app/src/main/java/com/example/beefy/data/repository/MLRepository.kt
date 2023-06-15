package com.example.beefy.data.repository

import android.util.Log
import com.example.beefy.data.response.ErrorResponse
import com.example.beefy.data.response.MlScanMeatResponse
import com.example.beefy.data.source.remote.ApiServices
import com.example.beefy.data.source.remote.ml.MlApiServices
import com.example.beefy.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import retrofit2.HttpException

class MLRepository(
    private val mlApiServices: MlApiServices
) {

    suspend fun MLscanMeat(
        file_image: MultipartBody.Part
    ) : Flow<Resource<MlScanMeatResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = mlApiServices.MLscanMeat(file_image)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("MLRepository", "ML scan meat HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun getError(e: HttpException): String {
        val errorMessage = e.response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorMessage, ErrorResponse::class.java)
        return errorResponse.message.toString()
    }

}