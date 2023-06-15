package com.example.beefy.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.beefy.data.response.BuyerOrderProductResponse
import com.example.beefy.data.response.CompleteOrder
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.data.response.EditBuyerResponse
import com.example.beefy.data.response.EditPPBuyerResponse
import com.example.beefy.data.response.ErrorResponse
import com.example.beefy.data.response.MlScanMeatResponse
import com.example.beefy.data.response.NewOrderResponse
import com.example.beefy.data.response.PaidOrderResponse
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.SaveScanResponse
import com.example.beefy.data.response.ScanMeatHistoryResponse
import com.example.beefy.data.response.ScanMeatResponse
import com.example.beefy.data.response.SearchStoreResponse
import com.example.beefy.data.response.TrendingStoreResponse
import com.example.beefy.data.response.UnpaidOrderResponse
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

class BuyerRepository(
    private val apiServices: ApiServices
) {

    suspend fun addPPBuyer(
        idBuyer: RequestBody,
        fileImage: MultipartBody.Part,
    ): Flow<Resource<EditPPBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.addPPBuyer("Bearer DAFTAR", idBuyer, fileImage)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "add PP buyer HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addDetailBuyer(
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
                val response = apiServices.addDetailBuyer(
                    "Bearer DAFTAR",
                    idPembeli.toInt(),
                    alamatLengkap,
                    namaPenerima,
                    nomorTelepon,
                    labelAlamat,
                    nama
                )
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "add pembeli HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun editPPBuyer(
        idBuyer: RequestBody,
        fileImage: MultipartBody.Part,
    ): Flow<Resource<EditPPBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editPPBuyer(idBuyer, fileImage)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "edit PP buyer HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun editDetailBuyer(
        idPembeli: String,
        alamatLengkap: String,
        namaPenerima: String,
        nomorTelepon: String,
        labelAlamat: String,
        nama : String
    ): Flow<Resource<EditBuyerResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.editDetailBuyer(
                    idPembeli.toInt(),
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailPembeliByIdAccount(
        idAccount: List<String>
    ) : Flow<Resource<List<DetailBuyerResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val list = mutableListOf<DetailBuyerResponse>()

                idAccount.forEach {
                    val response = apiServices.getDetailPembeliByIdAccount(it.toInt())
                    list.add(response)
                }

                Log.e(ContentValues.TAG, "getDetailPembeliByIdAccount: $list")

                emit(Resource.Success(list))
            }catch (e: HttpException) {
                Log.e("SellerRepository", "getDEtailPembeliByIdAccount HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun buyerGetUnpaidOrder(
        idPembeli: Int
    ) : Flow<Resource<List<UnpaidOrderResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.buyerUnpaidOrder(idPembeli)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("BuyerRepository", "UnpaidOrder HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun buyerGetPaidOrder(
        idPembeli: Int
    ) : Flow<Resource<List<PaidOrderResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.buyerPaidOrder(idPembeli)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("BuyerRepository", "PaidOrder HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun buyerGetOrderInProcess(
        idPembeli: Int
    ) : Flow<Resource<List<BuyerOrderProductResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.buyerOrderInProcess(idPembeli)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("BuyerRepository", "OrderInProcess HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }


    suspend fun buyerGetOrderInComplete(
        idPembeli: Int
    ) : Flow<Resource<List<BuyerOrderProductResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.buyerOrderInComplete(idPembeli)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("BuyerRepository", "OrderInComplete HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun buyerGetDetailOrder(
        idOrder: Int
    ) : Flow<Resource<DetailOrderResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getDetailOrder(idOrder)
                emit(Resource.Success(response))
            }catch (e: HttpException) {
                Log.e("BuyerRepository", "getDetailorder HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun finishOrder(
        idOrder: Int
    ) : Flow<Resource<CompleteOrder>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.acceptOrderComplete(idOrder)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "acceptordercomplete HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun saveScanResult(
        idPembeli:RequestBody,
        label:RequestBody,
        levelKesegaran:RequestBody,
        type:RequestBody,
        file_image: MultipartBody.Part
    ) : Flow<Resource<SaveScanResponse>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.saveScanResult(idPembeli, label, levelKesegaran, type, file_image)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "save scan HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
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
            }catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTrendingStore() : Flow<Resource<List<TrendingStoreResponse>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getTrendingStore()
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "gettrendingstore HttpException: " + e.message)
                emit(Resource.Error(getError(e)))
            } catch (e: Exception){
                emit(Resource.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTrendingProduct() : Flow<Resource<List<Product>>>{
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiServices.getTrendingProduct()
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                Log.e("BuyerRepository", "gettrendingproduct HttpException: " + e.message)
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