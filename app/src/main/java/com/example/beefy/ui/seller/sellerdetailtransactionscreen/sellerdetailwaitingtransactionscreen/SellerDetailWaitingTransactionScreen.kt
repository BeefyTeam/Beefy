package com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailwaitingtransactionscreen

import android.content.ContentValues.TAG
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
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.databinding.FragmentSellerDetailWaitingTransactionScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class SellerDetailWaitingTransactionScreen : Fragment() {

    private var _binding: FragmentSellerDetailWaitingTransactionScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idProduk: String
    private lateinit var idPembeli: String
    private lateinit var idPembayaran: String
    private lateinit var gambar:String

    private val sellerDetailWaitingTransactionViewModel: SellerDetailWaitingTransactionViewModel by inject()

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
        _binding =
            FragmentSellerDetailWaitingTransactionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupButton()
        setupObserver()


    }

    private fun setupObserver() {
        sellerDetailWaitingTransactionViewModel.buyerDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupShippingView(it.data)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "sellerDetailWaitingTransaction buyer detail setupObserver: " + it.error)
                }
            }
        }

        sellerDetailWaitingTransactionViewModel.acceptOrder.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_sellerDetailWaitingTransactionScreen_to_sellerTransactionScreen)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "sellerDetailWaitingTransaction accept order setupObserver: " + it.error)
                }
            }
        }

        sellerDetailWaitingTransactionViewModel.detailOrder.observe(viewLifecycleOwner){
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
                    Log.e(TAG, "sellerDetailWaitingTransaction detail ordersetupObserver: " + it.error)
                }
            }
        }
    }

    private fun setupOrderView(detailOrderResponse: DetailOrderResponse){
        binding.sellerDetailWaitingTransactionItemCard.meatDatePriceCardNoteItemPriceTv.text = detailOrderResponse.DetailRincian?.totalHarga.toString()
        binding.sellerDetailWaitingTransactionItemCard.meatDatePriceNoteCardItem.text = detailOrderResponse.Barang?.catatan
        binding.sellerDetailWaitingTransactionItemCard.meatDatePriceNoteCardDateTv.text = detailOrderResponse.Barang?.tanggalPesanan
        Glide.with(binding.root).load(gambar).into(binding.sellerDetailWaitingTransactionItemCard.meatDatePriceCardItemImageView)
        binding.sellerDetailWaitingTransactionItemCard.meatDatePriceNoteCardItemCountTv.text = detailOrderResponse.Barang?.totalBarang.toString()
        binding.sellerDetailWaitingTransactionItemCard.meatDatePriceNoteItemTitleTv.text = detailOrderResponse.Barang?.namaBarang

        Glide.with(binding.root).load(detailOrderResponse.BuktiPembayaran).into(binding.sellerDetailWaitingTransactionPaymentProofImageView)
    }

    private fun setupButton() {
        binding.sellerDetailWaitingTransactionAcceptBtn.setOnClickListener {
            sellerDetailWaitingTransactionViewModel.acceptOrder(idPembayaran.toInt())
        }
    }

    private fun initView() {
        sellerDetailWaitingTransactionViewModel.getBuyerDetail(idPembeli.toInt())
        sellerDetailWaitingTransactionViewModel.getDetailOrder(idPembayaran.toInt())
    }

    private fun setupShippingView(buyerDetail : DetailBuyerResponse){
        binding.sellerDetailWaitingTransactionAddressCard.addressSellerViewAddressTv.text = buyerDetail.alamatLengkap
        binding.sellerDetailWaitingTransactionAddressCard.addressSellerViewNameTv.text = buyerDetail.namaPenerima
        binding.sellerDetailWaitingTransactionAddressCard.addressSellerViewPhoneNumberTv.text = buyerDetail.nomorTelp
    }

    private fun setLoading(boolean: Boolean) {
        if (boolean) {
            binding.sellerDetailWaitingTransactionLinearLayout.loadSkeleton()
        } else {
            binding.sellerDetailWaitingTransactionLinearLayout.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}