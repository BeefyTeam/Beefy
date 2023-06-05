package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailcomplete

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.databinding.FragmentBuyerOrderDetailCompleteScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerOrderDetailCompleteScreen : Fragment() {

    private var _binding : FragmentBuyerOrderDetailCompleteScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idOrder : String
    private lateinit var idToko :String
    private lateinit var imgUrl : String

    private val buyerOrderDetailCompleteViewModel : BuyerOrderDetailCompleteViewModel by viewModel()

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
        _binding = FragmentBuyerOrderDetailCompleteScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupObserver()
        setupButton()

    }

    private fun initView(){
        buyerOrderDetailCompleteViewModel.getOrderDetail(idOrder.toInt())
    }

    private fun setupOrderView(detailOrderResponse: DetailOrderResponse){
        binding.buyerOrderDetailCompleteItemCard.meatDatePriceNoteCardItem.text = detailOrderResponse.Barang?.catatan
        binding.buyerOrderDetailCompleteItemCard.meatDatePriceNoteItemTitleTv.text = detailOrderResponse.Barang?.namaBarang
        Glide.with(binding.root).load(imgUrl).into(binding.buyerOrderDetailCompleteItemCard.meatDatePriceCardItemImageView)
        binding.buyerOrderDetailCompleteItemCard.meatDatePriceNoteCardItemCountTv.text = detailOrderResponse.Barang?.totalBarang.toString() + " Barang"
        binding.buyerOrderDetailCompleteItemCard.meatDatePriceNoteCardDateTv.text = detailOrderResponse.Barang?.tanggalPesanan
        binding.buyerOrderDetailCompleteItemCard.meatDatePriceCardNoteItemPriceTv.text = "Rp"+detailOrderResponse.Barang?.hargaBarang.toString()

        binding.buyerOrderDetailCompletePaymentDetailCard.paymentDetailCardTotalPriceAmountTv.text = "Rp"+detailOrderResponse.DetailRincian?.hargaBarang.toString()
        binding.buyerOrderDetailCompletePaymentDetailCard.paymentDetailCardShippingPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.biayaPengiriman.toString()
        binding.buyerOrderDetailCompletePaymentDetailCard.paymentDetailCardUniquePriceTv.text ="Rp"+ detailOrderResponse.DetailRincian?.kodeUnik.toString()
        binding.buyerOrderDetailCompletePaymentDetailCard.paymentDetailCardTotalPaymentPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.totalHarga.toString()

        Glide.with(binding.root).load(detailOrderResponse.BuktiPembayaran).into(binding.buyerOrderDetailCompletePaymentProofImageview)

        binding.buyerOrderDetailCompleteStoreCard.storeNameWithLocTitleTv.text = detailOrderResponse.Toko?.namaToko
        binding.buyerOrderDetailCompleteStoreCard.storeNameWithLocLocationTv.text = detailOrderResponse.Toko?.alamatToko
        Glide.with(binding.root).load(detailOrderResponse.Toko?.logoToko).into(binding.buyerOrderDetailCompleteStoreCard.storeNameWithLocImageView)
    }

    private fun setupObserver(){
        buyerOrderDetailCompleteViewModel.orderDetail.observe(viewLifecycleOwner){
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

        binding.buyerOrderDetailCompleteStoreCard.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idToko", idToko)
            findNavController().navigate(R.id.action_buyerOrderDetailCompleteScreen_to_buyerStoreDtailScreen, bundle)
        }

    }


    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerOrderDetailCompleteLinearLayout.loadSkeleton()
        }else{
            binding.buyerOrderDetailCompleteLinearLayout.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}