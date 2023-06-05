package com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailcompletetransactionscreen

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.databinding.FragmentSellerDetailCompleteTransactionScreenBinding
import com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailprocesstransactionscreen.SellerDetailProcessTransactionViewModel
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class SellerDetailCompleteTransactionScreen : Fragment() {

    private var _binding : FragmentSellerDetailCompleteTransactionScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idProduk: String
    private lateinit var idPembeli: String
    private lateinit var idPembayaran: String
    private lateinit var gambar:String

    private val sellerDetailCompleteTransactionViewModel : SellerDetailProcessTransactionViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idProduk = requireArguments().getString("idProduk").toString()
        idPembeli = requireArguments().getString("idPembeli").toString()
        idPembayaran = requireArguments().getString("idPembayaran").toString()
        gambar = requireArguments().getString("gambar").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerDetailCompleteTransactionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupObserver()
    }

    private fun initView(){
        sellerDetailCompleteTransactionViewModel.getBuyerDetail(idPembeli.toInt())
        sellerDetailCompleteTransactionViewModel.getDetailOrder(idPembayaran.toInt())
    }

    private fun setupObserver(){
        sellerDetailCompleteTransactionViewModel.buyerDetail.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupView(it.data)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "sellerDetailWaitingTransaction setupObserver: " + it.error)
                }
            }
        }

        sellerDetailCompleteTransactionViewModel.detailOrder.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupOrderView(it.data)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "sellerDetailWaitingTransaction detail ordersetupObserver: " + it.error)
                }
            }
        }

    }

    private fun setupOrderView(detailOrderResponse: DetailOrderResponse){
        binding.sellerDetailCompleteTransactionItemCard.meatDatePriceCardNoteItemPriceTv.text = detailOrderResponse.DetailRincian?.totalHarga.toString()
        binding.sellerDetailCompleteTransactionItemCard.meatDatePriceNoteCardItem.text = detailOrderResponse.Barang?.catatan
        binding.sellerDetailCompleteTransactionItemCard.meatDatePriceNoteCardDateTv.text = detailOrderResponse.Barang?.tanggalPesanan
        Glide.with(binding.root).load(gambar).into(binding.sellerDetailCompleteTransactionItemCard.meatDatePriceCardItemImageView)
        binding.sellerDetailCompleteTransactionItemCard.meatDatePriceNoteCardItemCountTv.text = detailOrderResponse.Barang?.totalBarang.toString()
        binding.sellerDetailCompleteTransactionItemCard.meatDatePriceNoteItemTitleTv.text = detailOrderResponse.Barang?.namaBarang

        Glide.with(binding.root).load(detailOrderResponse.BuktiPembayaran).into(binding.sellerDetailCompleteTransactionPaymentProofImageView)
    }


    private fun setupView(buyerDetail : DetailBuyerResponse){
        binding.sellerDetailCompleteTransactionAddressCard.addressSellerViewAddressTv.text = buyerDetail.alamatLengkap
        binding.sellerDetailCompleteTransactionAddressCard.addressSellerViewNameTv.text = buyerDetail.namaPenerima
        binding.sellerDetailCompleteTransactionAddressCard.addressSellerViewPhoneNumberTv.text = buyerDetail.nomorTelp
    }
    private fun setLoading(boolean: Boolean) {
        if (boolean) {
            binding.sellerDetailCompleteTransactionLinearLayout.loadSkeleton()
        } else {
            binding.sellerDetailCompleteTransactionLinearLayout.hideSkeleton()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}