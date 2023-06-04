package com.example.beefy.data.repository

import android.util.Log
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.EditBuyerResponse
import com.example.beefy.data.response.EditPPBuyerResponse
import com.example.beefy.data.response.EditPPPenjualResponse
import com.example.beefy.data.response.EditPenjualResponse
import com.example.beefy.data.response.ErrorResponse
import com.example.beefy.data.response.HelloWorldResponse
import com.example.beefy.data.response.NewOrderResponse
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.ScanMeatHistoryResponse
import com.example.beefy.data.response.ScanMeatResponse
import com.example.beefy.data.response.SearchStoreResponse
import com.example.beefy.data.response.UploadPaymentProofResponse
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
import retrofit2.http.Field
import retrofit2.http.Part

class BuyerRepository(
    private val apiServices: ApiServices
) {

    suspend fun editPPBuyer(
        idBuyer: RequestBody,
        fileImage: MultipartBody.Part,
    ): Flow<Resource<EditPPBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editPPBuyer("Bearer DAFTAR", idBuyer, fileImage)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "edit PP buyer HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun editBuyer(
        idPembeli: String,
        alamatLengkap: String,
        namaPenerima: String,
        nomorTelepon: String,
        labelAlamat: String,
        nama: String
    ): Flow<Resource<EditBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editBuyer(
                    "Bearer DAFTAR",
                    idPembeli,
                    alamatLengkap,
                    namaPenerima,
                    nomorTelepon,
                    labelAlamat,
                    nama
                )
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "edit pembeli HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun getDetailBuyer(
        idPembeli: Int
    ): Flow<Resource<DetailBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getDetailBuyer(idPembeli)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "detail pembeli HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun searchProduct(
        productName: String
    ): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.searchProduct(productName)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "search product HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun searchStore(
        storeName: String
    ): Flow<Resource<List<SearchStoreResponse>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.searchStore(storeName)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "search store HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun newOrder(
        idPembeli: Int,
        idToko: Int,
        idBarang: Int,
        rekening: String,
        catatan: String,
        alamatPengiriman: String,
        metodePembayaran: String,
        biayaPengiriman: Int,
        totalHarga: Int,
        kodeUnik: Int,
        totalBarang : Int
    ): Flow<Resource<NewOrderResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.addNewOrder(
                    idPembeli,
                    idToko,
                    idBarang,
                    rekening,
                    catatan,
                    alamatPengiriman,
                    metodePembayaran,
                    biayaPengiriman,
                    totalHarga,
                    kodeUnik,
                    totalBarang
                )
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "new order HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun uploadPaymentProof(
        idOrder:RequestBody,
        file_image: MultipartBody.Part
    ) : Flow<Resource<UploadPaymentProofResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.uploadPaymentProof(idOrder, file_image)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "upload payment proof HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun scanMeat(
        idPembeli:RequestBody,
        file_image: MultipartBody.Part
    ) : Flow<Resource<ScanMeatResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.scanMeat(idPembeli, file_image)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "scan meat HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }
        }
    }

    suspend fun scanMeatHistory(
        idPembeli: Int,
    ) : Flow<Resource<List<ScanMeatHistoryResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.scanMeatHistory(idPembeli)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "scanmeathistory HttpException: " + e.message)
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