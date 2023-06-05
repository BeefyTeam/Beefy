package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailonprocess

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.databinding.FragmentBuyerOrderDetailOnProcessScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerOrderDetailOnProcessScreen : Fragment() {

    private var _binding : FragmentBuyerOrderDetailOnProcessScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idOrder : String
    private lateinit var idToko :String
    private lateinit var imgUrl : String

    private val buyerOrderDetailOnProcessViewModel : BuyerOrderDetailOnProcessViewModel by viewModel()


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
        _binding = FragmentBuyerOrderDetailOnProcessScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupObserver()
        setupButton()

    }

    private fun initView(){
        buyerOrderDetailOnProcessViewModel.getOrderDetail(idOrder.toInt())
    }


    private fun setupOrderView(detailOrderResponse: DetailOrderResponse){
        binding.buyerOrderDetailOnProcessItemCard.meatDatePriceNoteCardItem.text = detailOrderResponse.Barang?.catatan
        binding.buyerOrderDetailOnProcessItemCard.meatDatePriceNoteItemTitleTv.text = detailOrderResponse.Barang?.namaBarang
        Glide.with(binding.root).load(imgUrl).into(binding.buyerOrderDetailOnProcessItemCard.meatDatePriceCardItemImageView)
        binding.buyerOrderDetailOnProcessItemCard.meatDatePriceNoteCardItemCountTv.text = detailOrderResponse.Barang?.totalBarang.toString() + " Barang"
        binding.buyerOrderDetailOnProcessItemCard.meatDatePriceNoteCardDateTv.text = detailOrderResponse.Barang?.tanggalPesanan
        binding.buyerOrderDetailOnProcessItemCard.meatDatePriceCardNoteItemPriceTv.text = "Rp"+detailOrderResponse.Barang?.hargaBarang.toString()

        binding.buyerOrderDetailOnProcessPaymentDetailCard.paymentDetailCardTotalPriceAmountTv.text = "Rp"+detailOrderResponse.DetailRincian?.hargaBarang.toString()
        binding.buyerOrderDetailOnProcessPaymentDetailCard.paymentDetailCardShippingPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.biayaPengiriman.toString()
        binding.buyerOrderDetailOnProcessPaymentDetailCard.paymentDetailCardUniquePriceTv.text ="Rp"+ detailOrderResponse.DetailRincian?.kodeUnik.toString()
        binding.buyerOrderDetailOnProcessPaymentDetailCard.paymentDetailCardTotalPaymentPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.totalHarga.toString()

        Glide.with(binding.root).load(detailOrderResponse.BuktiPembayaran).into(binding.buyerOrderDetailOnProcessPaymentProofImageview)

        binding.buyerOrderDetailOnProcessStoreCard.storeNameWithLocTitleTv.text = detailOrderResponse.Toko?.namaToko
        binding.buyerOrderDetailOnProcessStoreCard.storeNameWithLocLocationTv.text = detailOrderResponse.Toko?.alamatToko
        Glide.with(binding.root).load(detailOrderResponse.Toko?.logoToko).into(binding.buyerOrderDetailOnProcessStoreCard.storeNameWithLocImageView)
    }

    private fun setupObserver(){
        buyerOrderDetailOnProcessViewModel.orderDetail.observe(viewLifecycleOwner){
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

        buyerOrderDetailOnProcessViewModel.finishOrder.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    binding.buyerOrderDetailOnProcessProgressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.buyerOrderDetailOnProcessProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "buyerorderpaid setupObserver acceptorder: "+ it.error, )
                }

                is Resource.Success -> {
                    binding.buyerOrderDetailOnProcessProgressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_buyerOrderDetailOnProcessScreen_to_buyer_home_screen)
                }
            }
        }

    }

    private fun setupButton(){

        binding.buyerOrderDetailOnProcessStoreCard.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idToko", idToko)
            findNavController().navigate(R.id.action_buyerOrderDetailOnProcessScreen_to_buyerStoreDtailScreen, bundle)
        }

        binding.buyerOrderDetailOnProcessCompleteOrderBtn.setOnClickListener {
            buyerOrderDetailOnProcessViewModel.finishOrder(idOrder.toInt())
        }
    }


    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerOrderDetailOnProcessLinearLayout.loadSkeleton()
        }else{
            binding.buyerOrderDetailOnProcessLinearLayout.hideSkeleton()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}