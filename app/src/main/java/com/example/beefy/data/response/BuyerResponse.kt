package com.example.beefy.data.response

import com.google.gson.annotations.SerializedName

data class EditPPBuyerResponse(
    @SerializedName("message") var message: String? = null
)

data class EditBuyerResponse(
    @SerializedName("message") var message: String? = null
)

data class DetailBuyerResponse(
    @SerializedName("id_pembeli") var idPembeli: Int? = null,
    @SerializedName("nama") var nama: String? = null,
    @SerializedName("alamat_lengkap") var alamatLengkap: String? = null,
    @SerializedName("nama_penerima") var namaPenerima: String? = null,
    @SerializedName("nomor_telp") var nomorTelp: String? = null,
    @SerializedName("label_alamat") var labelAlamat: String? = null,
    @SerializedName("photo_profile") var photoProfile: String? = null,
    @SerializedName("user_account") var userAccount: UserAccount? = UserAccount()
)

data class UserAccount(
    @SerializedName("id_account") var idAccount: Int? = null,
    @SerializedName("email") var email: String? = null
)

data class SearchStoreResponse(
    @SerializedName("pk") var pk: Int? = null,
    @SerializedName("logo_toko") var logoToko: String? = null,
    @SerializedName("nama_toko") var namaToko: String? = null,
    @SerializedName("rekening") var rekening: String? = null,
    @SerializedName("metode_pembayaran") var metodePembayaran: String? = null,
    @SerializedName("alamat_lengkap") var alamatLengkap: String? = null,
    @SerializedName("nomor_telp") var nomorTelp: String? = null,
    @SerializedName("jam_operasional_buka") var jamOperasionalBuka: String? = null,
    @SerializedName("jam_operasional_tutup") var jamOperasionalTutup: String? = null
)

data class NewOrderResponse(
    @SerializedName("message") var message: String? = null,
    @SerializedName("id_order") var idOrder: Int? = null,
    @SerializedName("data_pembayaran") var dataPembayaran: DataPembayaran? = DataPembayaran()
)

data class DataPembayaran(

    @SerializedName("bank") var bank: String? = null,
    @SerializedName("nomor_rekening") var nomorRekening: String? = null,
    @SerializedName("atas_nama") var atasNama: String? = null,
    @SerializedName("total_pembayaran") var totalPembayaran: Int? = null

)

data class UploadPaymentProofResponse(
    @SerializedName("message") var message: String? = null
)

data class ScanMeatResponse(
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ScanMeatDataResponse? = ScanMeatDataResponse()
)

data class ScanMeatDataResponse(
    @SerializedName("url_gambar") var urlGambar: String? = null,
    @SerializedName("hasil") var hasil: String? = null,
    @SerializedName("level_kesegaran") var levelKesegaran: String? = null,
    @SerializedName("jenis") var jenis: String? = null
)

data class ScanMeatHistoryResponse(
    @SerializedName("gambar_url") var gambarUrl: String? = null,
    @SerializedName("tanggal") var tanggal: String? = null,
    @SerializedName("segar") var segar: Boolean? = null,
    @SerializedName("level_kesegaran") var levelKesegaran: Int? = null,
    @SerializedName("jenis") var jenis: String? = null
)

data class TrendingStoreResponse(
    @SerializedName("pk") var pk: Int? = null,
    @SerializedName("logo_toko") var logoToko: String? = null,
    @SerializedName("nama_toko") var namaToko: String? = null,
    @SerializedName("rekening") var rekening: String? = null,
    @SerializedName("metode_pembayaran") var metodePembayaran: String? = null,
    @SerializedName("alamat_lengkap") var alamatLengkap: String? = null,
    @SerializedName("nomor_telp") var nomorTelp: String? = null,
    @SerializedName("jam_operasional_buka") var jamOperasionalBuka: String? = null,
    @SerializedName("jam_operasional_tutup") var jamOperasionalTutup: String? = null
)

data class BuyerOrderProductResponse(
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

data class UnpaidOrderResponse(

    @SerializedName("bukti_bayar") var buktiBayar: String? = null,
    @SerializedName("rekening") var rekening: String? = null,
    @SerializedName("total_harga") var totalHarga: Int? = null,
    @SerializedName("biaya_pengiriman") var biayaPengiriman: Int? = null,
    @SerializedName("kode_unik") var kodeUnik: Int? = null,
    @SerializedName("FK_Order") var FKOrder: FKOrder? = FKOrder(),
    @SerializedName("status") var status: String? = null

)

data class PaidOrderResponse(

    @SerializedName("bukti_bayar") var buktiBayar: String? = null,
    @SerializedName("rekening") var rekening: String? = null,
    @SerializedName("total_harga") var totalHarga: Int? = null,
    @SerializedName("biaya_pengiriman") var biayaPengiriman: Int? = null,
    @SerializedName("kode_unik") var kodeUnik: Int? = null,
    @SerializedName("FK_Order") var FKOrder: FKOrder? = FKOrder(),
    @SerializedName("status") var status: String? = null

)

data class FKOrder(
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

data class CompleteOrder(
    @SerializedName("message") var message: String? = null
)

data class MlScanMeatResponse(
    @SerializedName("label") var label: String? = null,
    @SerializedName("kesegaran") var kesegaran: String? = null,
    @SerializedName("type") var type: String? = null
)


data class SaveScanResponse(

    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: DataSaveScan? = DataSaveScan()

)

data class DataSaveScan(
    @SerializedName("id_history") var idHistory: Int? = null,
    @SerializedName("url_gambar") var urlGambar: String? = null,
    @SerializedName("hasil") var hasil: String? = null,
    @SerializedName("level_kesegaran") var levelKesegaran: Int? = null,
    @SerializedName("jenis") var jenis: String? = null
)


