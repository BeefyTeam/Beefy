package com.example.beefy.data.source.remote

import com.example.beefy.data.response.AddProductResponse
import com.example.beefy.data.response.DeleteProductResponse
import com.example.beefy.data.response.EditBuyerResponse
import com.example.beefy.data.response.EditPPBuyerResponse
import com.example.beefy.data.response.EditPPPenjualResponse
import com.example.beefy.data.response.EditPenjualResponse
import com.example.beefy.data.response.EditProductResponse
import com.example.beefy.data.response.Product
import com.example.beefy.data.response.HelloWorldResponse
import com.example.beefy.data.response.LoginResponse
import com.example.beefy.data.response.RefreshTokenResponse
import com.example.beefy.data.response.RegisterBuyerResponse
import com.example.beefy.data.response.RegisterPenjualResponse
import com.example.beefy.data.response.SellerOrderProductResponse
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
    ) : LoginResponse

    @FormUrlEncoded
    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Field("token_refresh") refreshTokenBody: String
    ) : RefreshTokenResponse

    @GET("")
    suspend fun helloWorld() : HelloWorldResponse

    @FormUrlEncoded
    @POST("penjual/register-penjual/")
    suspend fun registerPenjual(
        @Header("Authorization") token: String,
        @Field("nama_toko") namaToko : String,
        @Field("nomor_telepon") nomorTelepon : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : RegisterPenjualResponse

    @FormUrlEncoded
    @POST("pembeli/register-pembeli/")
    suspend fun registerPembeli(
        @Header("Authorization") token: String,
        @Field("nama") nama : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : RegisterBuyerResponse


    //penjual
    @Multipart
    @POST("penjual/edit-pp-penjual/")
    suspend fun editPPPenjual(
        @Header("Authorization") token: String,
        @Part("id_toko") idToko : RequestBody,
        @Part file_image: MultipartBody.Part,
    ) : EditPPPenjualResponse

    @FormUrlEncoded
    @POST("penjual/edit-penjual/")
    suspend fun editPenjual(
        @Header("Authorization") token: String,
        @Field("id_toko") idToko : String,
        @Field("alamat_lengkap") alamatLengkap : String,
        @Field("jam_operasional_buka") jamBuka : String,
        @Field("jam_operasional_tutup") jamTutup : String,
        @Field("metode_pembayaran") metodePembayaran:String,
        @Field("rekening") rekening:String
    ) : EditPenjualResponse

    @GET("penjual/get-products/{idToko}")
    suspend fun getProducts(
        @Path("idToko") idToko: Int
    ) : List<Product>

    @Multipart
    @POST("penjual/add-product/")
    suspend fun addProduct(
        @Part("id_toko") idToko: RequestBody,
        @Part("nama_barang") namaBarang : RequestBody,
        @Part("deskripsi") deskripsi : RequestBody,
        @Part("harga") harga : RequestBody,
        @Part file_image: MultipartBody.Part
    ) : AddProductResponse

    @Multipart
    @POST("penjual/edit-product/")
    suspend fun editProduct(
        @Part("id_product") idToko: RequestBody,
        @Part("nama_barang") namaBarang : RequestBody,
        @Part("deskripsi") deskripsi : RequestBody,
        @Part("harga") harga : RequestBody,
        @Part file_image: MultipartBody.Part
    ) : EditProductResponse

    @DELETE("penjual/delete-product/{id_product}")
    suspend fun deleteProduct(
        @Path("id_product") idProduct: Int
    ) : DeleteProductResponse

    //orders penjual
    @GET("order/order-in-waiting?id_toko={idToko}")
    suspend fun sellerOrderWaiting(
        @Query("idToko") idToko: Int
    ) : SellerOrderProductResponse


    //pembeli
    @Multipart
    @POST("pembeli/edit-pp-pembeli/")
    suspend fun editPPBuyer(
        @Header("Authorization") token: String,
        @Part("id_pembeli") idBuyer : RequestBody,
        @Part file_image: MultipartBody.Part,
    ) : EditPPBuyerResponse

    @FormUrlEncoded
    @POST("pembeli/edit-pembeli/")
    suspend fun editBuyer(
        @Header("Authorization") token: String,
        @Field("id_pembeli") idPembeli:String,
        @Field("alamat_lengkap") alamatLengkap : String,
        @Field("nama_penerima") namaPenerima : String,
        @Field("nomor_telepon") nomorTelepon : String,
        @Field("label_alamat") labelAlamat : String,
        @Field("nama") nama : String,
    ) : EditBuyerResponse


    //orders




}