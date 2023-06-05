package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailpaid

import android.app.Activity
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.databinding.FragmentBuyerOrderDetailPaidScreenBinding
import com.example.beefy.utils.Resource
import com.example.beefy.utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class BuyerOrderDetailPaidScreen : Fragment() {

    private var _binding : FragmentBuyerOrderDetailPaidScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idOrder : String
    private lateinit var idToko :String
    private lateinit var imgUrl : String

    private val buyerOrderDetailPaidViewModel : BuyerOrderDetailPaidViewModel by viewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idOrder = requireArguments().getString("idOrder").toString()
        idToko = requireArguments().getString("idToko").toString()
        imgUrl = requireArguments().getString("imgUrl").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerOrderDetailPaidScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupObserver()
        setupButton()
    }

    private fun initView(){
        buyerOrderDetailPaidViewModel.getOrderDetail(idOrder.toInt())
    }


    private fun setupOrderView(detailOrderResponse: DetailOrderResponse){
        binding.buyerOrderDetailPaidItemCard.meatDatePriceNoteCardItem.text = detailOrderResponse.Barang?.catatan
        binding.buyerOrderDetailPaidItemCard.meatDatePriceNoteItemTitleTv.text = detailOrderResponse.Barang?.namaBarang
        Glide.with(binding.root).load(imgUrl).into(binding.buyerOrderDetailPaidItemCard.meatDatePriceCardItemImageView)
        binding.buyerOrderDetailPaidItemCard.meatDatePriceNoteCardItemCountTv.text = detailOrderResponse.Barang?.totalBarang.toString() + " Barang"
        binding.buyerOrderDetailPaidItemCard.meatDatePriceNoteCardDateTv.text = detailOrderResponse.Barang?.tanggalPesanan
        binding.buyerOrderDetailPaidItemCard.meatDatePriceCardNoteItemPriceTv.text = "Rp"+detailOrderResponse.Barang?.hargaBarang.toString()

        binding.buyerOrderDetailPaidPaymentDetailCard.paymentDetailCardTotalPriceAmountTv.text = "Rp"+detailOrderResponse.DetailRincian?.hargaBarang.toString()
        binding.buyerOrderDetailPaidPaymentDetailCard.paymentDetailCardShippingPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.biayaPengiriman.toString()
        binding.buyerOrderDetailPaidPaymentDetailCard.paymentDetailCardUniquePriceTv.text ="Rp"+ detailOrderResponse.DetailRincian?.kodeUnik.toString()
        binding.buyerOrderDetailPaidPaymentDetailCard.paymentDetailCardTotalPaymentPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.totalHarga.toString()

        Glide.with(binding.root).load(detailOrderResponse.BuktiPembayaran).into(binding.buyerOrderDetailPaidPaymentProofImageview)

        binding.buyerOrderDetailPaidStoreCard.storeNameWithLocTitleTv.text = detailOrderResponse.Toko?.namaToko
        binding.buyerOrderDetailPaidStoreCard.storeNameWithLocLocationTv.text = detailOrderResponse.Toko?.alamatToko
        Glide.with(binding.root).load(detailOrderResponse.Toko?.logoToko).into(binding.buyerOrderDetailPaidStoreCard.storeNameWithLocImageView)
    }

    private fun setupObserver(){
        buyerOrderDetailPaidViewModel.orderDetail.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "buyerorderpaid setupObserver order detail: "+ it.error, )
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupOrderView(it.data)
                }
            }
        }

    }

    private fun setupButton(){

        binding.buyerOrderDetailPaidStoreCard.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idToko", idToko)
            findNavController().navigate(R.id.action_buyerOrderDetailPaidScreen_to_buyerStoreDtailScreen, bundle)
        }
    }


    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerOrderDetailPaidLinearLayout.loadSkeleton()
        }else{
            binding.buyerOrderDetailPaidLinearLayout.hideSkeleton()
        }
    }


}