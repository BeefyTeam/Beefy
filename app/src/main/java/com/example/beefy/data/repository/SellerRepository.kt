package com.example.beefy.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.beefy.data.response.AcceptOrderResponse
import com.example.beefy.data.response.AddProductResponse
import com.example.beefy.data.response.DeleteProductResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.data.response.EditPPPenjualResponse
import com.example.beefy.data.response.EditPenjualResponse
import com.example.beefy.data.response.EditProductResponse
import com.example.beefy.data.response.ErrorResponse
import com.example.beefy.data.response.PaidOrderResponse
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.SellerOrderProductResponse
import com.example.beefy.data.source.remote.ApiServices
import com.example.beefy.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class SellerRepository(
    private val apiServices: ApiServices
) {

    suspend fun addPPPenjual(
        idToko: RequestBody,
        fileImage : MultipartBody.Part,
    ) : Flow<Resource<EditPPPenjualResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.addPPPenjual("Bearer DAFTAR", idToko, fileImage)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "add PP penjual HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun addDetailPenjual(
        idToko: String,
        alamatLengkap:String,
        jamBuka:String,
        jamTutup:String,
        metodePembayaran:String,
        rekening:String
    ) : Flow<Resource<EditPenjualResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.addDetailPenjual("Bearer DAFTAR", idToko, alamatLengkap, jamBuka, jamTutup, metodePembayaran, rekening)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "add penjual HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun editPPPenjual(
        idToko: RequestBody,
        fileImage : MultipartBody.Part,
    ) : Flow<Resource<EditPPPenjualResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editPPPenjual(idToko, fileImage)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "edit PP penjual HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun editDetailPenjual(
        idToko: String,
        alamatLengkap:String,
        jamBuka:String,
        jamTutup:String,
        metodePembayaran:String,
        rekening:String
    ) : Flow<Resource<EditPenjualResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editDetailPenjual(idToko, alamatLengkap, jamBuka, jamTutup, metodePembayaran, rekening)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "edit penjual HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }


    suspend fun getProducts(
        idToko: Int
    ) : Flow<Resource<List<Product>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getProducts(idToko)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "getProducts HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun addProduct(
        idToko: RequestBody,
        namaBarang : RequestBody,
        deskripsi : RequestBody,
        harga : RequestBody,
        fileImage: MultipartBody.Part
    ) : Flow<Resource<AddProductResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.addProduct(idToko, namaBarang, deskripsi, harga, fileImage)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "addProducts HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun editProduct(
        idProduct: RequestBody,
        namaBarang : RequestBody,
        deskripsi : RequestBody,
        harga : RequestBody,
        fileImage: MultipartBody.Part
    ) : Flow<Resource<EditProductResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editProduct(idProduct, namaBarang, deskripsi, harga, fileImage)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "editProducts HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun deleteProduct(
        idProduct: Int,
    ) : Flow<Resource<DeleteProductResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.deleteProduct(idProduct)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "deleteProducts HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun sellerGetPaidOrder(
        idToko: Int
    ) : Flow<Resource<List<PaidOrderResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.sellerPaidOrder(idToko)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "PaidOrder HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun sellerGetOrderInWaiting(
        idToko: Int
    ) : Flow<Resource<List<SellerOrderProductResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.sellerOrderInWaiting(idToko)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "OrderInWaiting HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun sellerGetOrderInProcess(
        idToko: Int
    ) : Flow<Resource<List<SellerOrderProductResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.sellerOrderInProcess(idToko)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "OrderInProcess HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun sellerGetOrderInComplete(
        idToko: Int
    ) : Flow<Resource<List<SellerOrderProductResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.sellerOrderInComplete(idToko)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "OrderInComplete HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun sellerAcceptOrder(
        idOrder: Int
    ) : Flow<Resource<AcceptOrderResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.sellerAcceptOrder(idOrder)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "acceptorder HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun sellerGetDetailOrder(
        idOrder: Int
    ) : Flow<Resource<DetailOrderResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getDetailOrder(idOrder)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "getDetailorder HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }



    suspend fun getDetailPenjual(
        idToko: Int
    ) : Flow<Resource<DetailPenjualResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getDetailPenjual(idToko)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "getDEtailPenjual HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun getDetailPenjualByIdAccount(
        idAccount: List<String>
    ) : Flow<Resource<List<DetailPenjualResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                var list = mutableListOf<DetailPenjualResponse>()

                idAccount.forEach {
                    val response = apiServices.getDetailPenjualByIdAccount(it.toInt())
                    list.add(response)
                }

                Log.e(TAG, "getDetailPenjualByIdAccount: "+ list, )

                emit(Resource.Success(list))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "getDEtailPenjualByIdAccount HttpException: " + e.message)
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