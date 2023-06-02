package com.example.beefy.data.response

import com.google.gson.annotations.SerializedName

data class EditPPPenjualResponse (
    @SerializedName("message"    ) var message   : String? = null,
    @SerializedName("url_gambar" ) var urlGambar : String? = null
)

data class EditPenjualResponse (
    @SerializedName("message"  ) var message  : String? = null,
)

data class Product (

    @SerializedName("pk"          ) var pk         : Int?    = null,
    @SerializedName("ID_TOKO"     ) var IDTOKO     : Int?    = null,
    @SerializedName("nama_barang" ) var namaBarang : String? = null,
    @SerializedName("deskripsi"   ) var deskripsi  : String? = null,
    @SerializedName("harga"       ) var harga      : Int?    = null

)

data class AddProductResponse (
    @SerializedName("message"  ) var message  : String? = null,
)

data class EditProductResponse (
    @SerializedName("message"  ) var message  : String? = null,
)

data class DeleteProductResponse (
    @SerializedName("message"  ) var message  : String? = null,
)

data class SellerOrderProductResponse (
    @SerializedName("pk"                ) var pk               : Int?    = null,
    @SerializedName("ID_PEMBAYARAN"     ) var IDPEMBAYARAN     : Int?    = null,
    @SerializedName("ID_PEMBELI"        ) var IDPEMBELI        : Int?    = null,
    @SerializedName("ID_TOKO"           ) var IDTOKO           : Int?    = null,
    @SerializedName("ID_BARANG"         ) var IDBARANG         : Int?    = null,
    @SerializedName("catatan"           ) var catatan          : String? = null,
    @SerializedName("alamat_pengiriman" ) var alamatPengiriman : String? = null,
    @SerializedName("metode_pembayaran" ) var metodePembayaran : String? = null,
    @SerializedName("tanggal_order"     ) var tanggalOrder     : String? = null,
    @SerializedName("status"            ) var status           : String? = null
)