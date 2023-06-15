package com.example.beefy.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class EditPPPenjualResponse(
    @SerializedName("message") var message: String? = null,
    @SerializedName("url_gambar") var urlGambar: String? = null
)

data class EditPenjualResponse(
    @SerializedName("message") var message: String? = null,
)

data class DetailPenjualResponse(
    @SerializedName("id_toko") var idToko: Int? = null,
    @SerializedName("logo_toko") var logoToko: String? = null,
    @SerializedName("nama_toko") var namaToko: String? = null,
    @SerializedName("rekening") var rekening: String? = null,
    @SerializedName("metode_pembayaran") var metodePembayaran: String? = null,
    @SerializedName("alamat_lengkap") var alamatLengkap: String? = null,
    @SerializedName("nomor_telp") var nomorTelp: String? = null,
    @SerializedName("jam_operasional_buka") var jamOperasionalBuka: String? = null,
    @SerializedName("jam_operasional_tutup") var jamOperasionalTutup: String? = null,
    @SerializedName("user_account") var userAccount: UserAccount? = UserAccount()
)


data class Product(

    @SerializedName("pk") var pk: Int? = null,
    @SerializedName("gambar") var gambar: String? = null,
    @SerializedName("ID_TOKO") var IDTOKO: Int? = null,
    @SerializedName("nama_barang") var namaBarang: String? = null,
    @SerializedName("deskripsi") var deskripsi: String? = null,
    @SerializedName("harga") var harga: Int? = null

)

data class AddProductResponse(
    @SerializedName("message") var message: String? = null,
)

data class EditProductResponse(
    @SerializedName("message") var message: String? = null,
)

data class DeleteProductResponse(
    @SerializedName("message") var message: String? = null,
)

data class SellerOrderProductResponse(
    @SerializedName("pk") var pk: Int? = null,
    @SerializedName("ID_PEMBAYARAN") var IDPEMBAYARAN: Int? = null,
    @SerializedName("ID_PEMBELI") var IDPEMBELI: Int? = null,
    @SerializedName("ID_TOKO") var IDTOKO: Int? = null,
    @SerializedName("ID_BARANG") var IDBARANG: IDBARANG? = IDBARANG(),
    @SerializedName("total_barang") var totalBarang: Int? = null,
    @SerializedName("catatan") var catatan: String? = null,
    @SerializedName("alamat_pengiriman") var alamatPengiriman: String? = null,
    @SerializedName("metode_pembayaran") var metodePembayaran: String? = null,
    @SerializedName("tanggal_order") var tanggalOrder: String? = null,
    @SerializedName("status") var status: String? = null
)

data class IDBARANG(

    @SerializedName("nama_barang") var namaBarang: String? = null,
    @SerializedName("deskripsi") var deskripsi: String? = null,
    @SerializedName("gambar") var gambar: String? = null,
    @SerializedName("harga") var harga: Int? = null

)

data class AcceptOrderResponse(
    @SerializedName("message") var message: String? = null
)


data class DetailOrderResponse(
    @SerializedName("message") var message: String? = null,
    @SerializedName("Barang") var Barang: Barang? = Barang(),
    @SerializedName("Bukti pembayaran") var BuktiPembayaran: String? = null,
    @SerializedName("Toko") var Toko: Toko? = Toko(),
    @SerializedName("Detail pembeli") var DetailPembeli: DetailPembeli? = DetailPembeli(),
    @SerializedName("Detail rincian") var DetailRincian: DetailRincian? = DetailRincian()
)

data class Barang(
    @SerializedName("nama barang") var namaBarang: String? = null,
    @SerializedName("harga barang") var hargaBarang: Int? = null,
    @SerializedName("tanggal pesanan") var tanggalPesanan: String? = null,
    @SerializedName("catatan") var catatan: String? = null,
    @SerializedName("total barang") var totalBarang: Int? = null
)

data class Toko(
    @SerializedName("logo toko") var logoToko: String? = null,
    @SerializedName("nama toko") var namaToko: String? = null,
    @SerializedName("nomor telepon toko") var nomorTeleponToko: String? = null,
    @SerializedName("alamat toko") var alamatToko: String? = null
)

data class DetailPembeli(
    @SerializedName("nama_penerima") var namaPenerima: String? = null,
    @SerializedName("alamat") var alamat: String? = null,
    @SerializedName("nomor telepon pembeli") var nomorTeleponPembeli: String? = null
)

data class DetailRincian(

    @SerializedName("harga barang") var hargaBarang: Int? = null,
    @SerializedName("biaya pengiriman") var biayaPengiriman: Int? = null,
    @SerializedName("kode unik") var kodeUnik: Int? = null,
    @SerializedName("total harga") var totalHarga: Int? = null

)

