package com.example.beefy.data.source.remote.ml

import com.example.beefy.data.response.MlScanMeatResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MlApiServices {

    @Multipart
    @POST("predict/")
    suspend fun MLscanMeat(
        @Part file_image: MultipartBody.Part
    ): MlScanMeatResponse

}