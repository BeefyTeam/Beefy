package com.example.beefy.data.response

import com.google.gson.annotations.SerializedName


data class ErrorResponse (
    @SerializedName("message"  ) var message  : String? = null,
    )

data class LoginResponse (
    @SerializedName("id_account"    ) var idUser    : Int?    = null,
    @SerializedName("jenis_akun" ) var jenisAkun : String? = null,
    @SerializedName("id_pembeli" ) var idPembeli : Int?    = null,
    @SerializedName("id_toko" ) var idToko : Int?    = null,
    @SerializedName("refresh"    ) var refresh   : String? = null,
    @SerializedName("access"     ) var access    : String? = null

)

data class RefreshTokenResponse(
    @SerializedName("token_access") var tokenAccess: String? = null
)

data class RegisterPenjualResponse (
    @SerializedName("message"   ) var message  : String? = null,
    @SerializedName("id_user"   ) var idUser   : Int?    = null,
    @SerializedName("id_toko"   ) var idToko   : Int?    = null,
    @SerializedName("nama_toko" ) var namaToko : String? = null
)

data class RegisterBuyerResponse (

    @SerializedName("message"    ) var message   : String? = null,
    @SerializedName("id_user"    ) var idUser    : Int?    = null,
    @SerializedName("id_pembeli" ) var idPembeli : Int?    = null

)

data class ForgotPasswordResponse (

    @SerializedName("message"    ) var message   : String? = null,

)

