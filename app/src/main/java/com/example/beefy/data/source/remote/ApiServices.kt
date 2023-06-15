package com.example.beefy.data.source.remote

import com.example.beefy.data.response.AcceptOrderResponse
import com.example.beefy.data.response.AddProductResponse
import com.example.beefy.data.response.BuyerOrderProductResponse
import com.example.beefy.data.response.CompleteOrder
import com.example.beefy.data.response.DeleteProductResponse
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.data.response.EditBuyerResponse
import com.example.beefy.data.response.EditPPBuyerResponse
import com.example.beefy.data.response.EditPPPenjualResponse
import com.example.beefy.data.response.EditPenjualResponse
import com.example.beefy.data.response.EditProductResponse
import com.example.beefy.data.response.ForgotPasswordResponse
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.LoginResponse
import com.example.beefy.data.response.MlScanMeatResponse
import com.example.beefy.data.response.NewOrderResponse
import com.example.beefy.data.response.PaidOrderResponse
import com.example.beefy.data.response.RefreshTokenResponse
import com.example.beefy.data.response.RegisterBuyerResponse
import com.example.beefy.data.response.RegisterPenjualResponse
import com.example.beefy.data.response.SaveScanResponse
import com.example.beefy.data.response.ScanMeatHistoryResponse
import com.example.beefy.data.response.ScanMeatResponse
import com.example.beefy.data.response.SearchStoreResponse
import com.example.beefy.data.response.SellerOrderProductResponse
import com.example.beefy.data.response.TrendingStoreResponse
import com.example.beefy.data.response.UnpaidOrderResponse
import com.example.beefy.data.response.UploadPaymentProofResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    //auth
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Field("token_refresh") refreshTokenBody: String
    ): RefreshTokenResponse


    @FormUrlEncoded
    @POST("penjual/register-penjual/")
    suspend fun registerPenjual(
        @Header("Authorization") token: String,
        @Field("nama_toko") namaToko: String,
        @Field("nomor_telepon") nomorTelepon: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterPenjualResponse

    @FormUrlEncoded
    @POST("pembeli/register-pembeli/")
    suspend fun registerPembeli(
        @Header("Authorization") token: String,
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterBuyerResponse

    @FormUrlEncoded
    @POST("auth/forgot-password/")
    suspend fun forgotPassword(
        @Field("email") email: String,
        @Field("new_password") newPassword: String
    ): ForgotPasswordResponse


    //penjual
    @Multipart
    @POST("penjual/edit-pp-penjual/")
    suspend fun addPPPenjual(
        @Header("Authorization") token: String,
        @Part("id_toko") idToko: RequestBody,
        @Part file_image: MultipartBody.Part,
    ): EditPPPenjualResponse

    @FormUrlEncoded
    @POST("penjual/edit-penjual/")
    suspend fun addDetailPenjual(
        @Header("Authorization") token: String,
        @Field("id_toko") idToko: String,
        @Field("alamat_lengkap") alamatLengkap: String,
        @Field("jam_operasional_buka") jamBuka: String,
        @Field("jam_operasional_tutup") jamTutup: String,
        @Field("metode_pembayaran") metodePembayaran: String,
        @Field("rekening") rekening: String
    ): EditPenjualResponse

    @Multipart
    @POST("penjual/edit-pp-penjual/")
    suspend fun editPPPenjual(
        @Part("id_toko") idToko: RequestBody,
        @Part file_image: MultipartBody.Part,
    ): EditPPPenjualResponse

    @FormUrlEncoded
    @POST("penjual/edit-penjual/")
    suspend fun editDetailPenjual(
        @Field("id_toko") idToko: String,
        @Field("alamat_lengkap") alamatLengkap: String,
        @Field("jam_operasional_buka") jamBuka: String,
        @Field("jam_operasional_tutup") jamTutup: String,
        @Field("metode_pembayaran") metodePembayaran: String,
        @Field("rekening") rekening: String
    ): EditPenjualResponse

    @GET("penjual/user/detail/by-id-toko/{id_toko}")
    suspend fun getDetailPenjual(
        @Path("id_toko") idToko: Int
    ): DetailPenjualResponse

    @GET("penjual/user/detail/by-id-account/{id_account}")
    suspend fun getDetailPenjualByIdAccount(
        @Path("id_account") idAccount: Int
    ): DetailPenjualResponse

    @GET("penjual/get-products/{idToko}")
    suspend fun getProducts(
        @Path("idToko") idToko: Int
    ): List<Product>

    @Multipart
    @POST("penjual/add-product/")
    suspend fun addProduct(
        @Part("id_toko") idToko: RequestBody,
        @Part("nama_barang") namaBarang: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part file_image: MultipartBody.Part
    ): AddProductResponse

    @Multipart
    @POST("penjual/edit-product/")
    suspend fun editProduct(
        @Part("id_product") idToko: RequestBody,
        @Part("nama_barang") namaBarang: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("harga") harga: RequestBody,
        @Part file_image: MultipartBody.Part
    ): EditProductResponse

    @DELETE("penjual/delete-product/{id_product}")
    suspend fun deleteProduct(
        @Path("id_product") idProduct: Int
    ): DeleteProductResponse

    //orders penjual
    @GET("order/order-sudah-bayar/")
    suspend fun sellerPaidOrder(
        @Query("id_toko") idToko: Int
    ): List<PaidOrderResponse>

    @GET("order/order-in-process/")
    suspend fun sellerOrderInProcess(
        @Query("id_toko") idToko: Int
    ): List<SellerOrderProductResponse>

    @GET("order/order-in-complete")
    suspend fun sellerOrderInComplete(
        @Query("id_toko") idToko: Int
    ): List<SellerOrderProductResponse>

    @FormUrlEncoded
    @POST("penjual/accept-order/")
    suspend fun sellerAcceptOrder(
        @Field("id_order") idOrder: Int
    ): AcceptOrderResponse

    @GET("order/detail/{idOrder}")
    suspend fun getDetailOrder(
        @Path("idOrder") idOrder: Int
    ): DetailOrderResponse


    //pembeli
    @Multipart
    @POST("pembeli/edit-pp-pembeli/")
    suspend fun addPPBuyer(
        @Header("Authorization") token: String,
        @Part("id_pembeli") idBuyer: RequestBody,
        @Part file_image: MultipartBody.Part,
    ): EditPPBuyerResponse

    @FormUrlEncoded
    @POST("pembeli/edit-pembeli/")
    suspend fun addDetailBuyer(
        @Header("Authorization") token: String,
        @Field("id_pembeli") idPembeli: Int,
        @Field("alamat_lengkap") alamatLengkap: String,
        @Field("nama_penerima") namaPenerima: String,
        @Field("nomor_telp") nomorTelepon: String,
        @Field("label_alamat") labelAlamat: String,
        @Field("nama") nama: String,
    ): EditBuyerResponse

    @Multipart
    @POST("pembeli/edit-pp-pembeli/")
    suspend fun editPPBuyer(
        @Part("id_pembeli") idBuyer: RequestBody,
        @Part file_image: MultipartBody.Part,
    ): EditPPBuyerResponse

    @FormUrlEncoded
    @POST("pembeli/edit-pembeli/")
    suspend fun editDetailBuyer(
        @Field("id_pembeli") idPembeli: Int,
        @Field("alamat_lengkap") alamatLengkap: String,
        @Field("nama_penerima") namaPenerima: String,
        @Field("nomor_telp") nomorTelepon: String,
        @Field("label_alamat") labelAlamat: String,
        @Field("nama") nama: String
    ): EditBuyerResponse


    @GET("pembeli/user/detail/by-id-pembeli/{idPembeli}")
    suspend fun getDetailBuyer(
        @Path("idPembeli") idPembeli: Int
    ): DetailBuyerResponse

    @GET("pembeli/user/detail/by-id-account/{id_account}")
    suspend fun getDetailPembeliByIdAccount(
        @Path("id_account") idAccount: Int
    ): DetailBuyerResponse

    @GET("pembeli/search-product/")
    suspend fun searchProduct(
        @Query("product_name") productName: String
    ): List<Product>

    @GET("pembeli/search-toko/")
    suspend fun searchStore(
        @Query("toko_name") tokoName: String
    ): List<SearchStoreResponse>

    @FormUrlEncoded
    @POST("order/new-order/")
    suspend fun addNewOrder(
        @Field("ID_PEMBELI") idPembeli: Int,
        @Field("ID_TOKO") idToko: Int,
        @Field("ID_BARANG") idBarang: Int,
        @Field("rekening") rekening: String,
        @Field("catatan") catatan: String,
        @Field("alamat_pengiriman") alamatPengiriman: String,
        @Field("metode_pembayaran") metodePembayaran: String,
        @Field("biaya_pengiriman") biayaPengiriman: Int,
        @Field("total_harga") totalHarga: Int,
        @Field("kode_unik") kodeUnik: Int,
        @Field("total_barang") totalBarang: Int
    ): NewOrderResponse


    @Multipart
    @POST("order/upload-bukti/")
    suspend fun uploadPaymentProof(
        @Part("id_order") idOrder: RequestBody,
        @Part file_image: MultipartBody.Part
    ): UploadPaymentProofResponse


    @GET("order/order-belum-bayar/")
    suspend fun buyerUnpaidOrder(
        @Query("id_pembeli") idPembeli: Int
    ): List<UnpaidOrderResponse>

    @GET("order/order-sudah-bayar/")
    suspend fun buyerPaidOrder(
        @Query("id_pembeli") idPembeli: Int
    ): List<PaidOrderResponse>

    @GET("order/order-in-process/")
    suspend fun buyerOrderInProcess(
        @Query("id_pembeli") idPembeli: Int
    ): List<BuyerOrderProductResponse>

    @GET("order/order-in-complete")
    suspend fun buyerOrderInComplete(
        @Query("id_pembeli") idPembeli: Int
    ): List<BuyerOrderProductResponse>


    @Multipart
    @POST("pembeli/scan-meat/")
    suspend fun scanMeat(
        @Part("id_pembeli") idPembeli: RequestBody,
        @Part file_image: MultipartBody.Part
    ): ScanMeatResponse


    @Multipart
    @POST("pembeli/save-scan-result/")
    suspend fun saveScanResult(
        @Part("id_pembeli") idPembeli: RequestBody,
        @Part("label") label: RequestBody,
        @Part("level_kesegaran") levelKesegaran: RequestBody,
        @Part("type") type: RequestBody,
        @Part file_image: MultipartBody.Part
    ) : SaveScanResponse

    @GET("pembeli/scan-history/{idPembeli}")
    suspend fun scanMeatHistory(
        @Path("idPembeli") idPembeli: Int
    ): List<ScanMeatHistoryResponse>

    @GET("pembeli/get-trending-store")
    suspend fun getTrendingStore(): List<TrendingStoreResponse>

    @GET("pembeli/get-trending-product")
    suspend fun getTrendingProduct(): List<Product>

    @FormUrlEncoded
    @POST("pembeli/accept-order-complete/")
    suspend fun acceptOrderComplete(
        @Field("id_order") idOrder: Int
    ): CompleteOrder


}