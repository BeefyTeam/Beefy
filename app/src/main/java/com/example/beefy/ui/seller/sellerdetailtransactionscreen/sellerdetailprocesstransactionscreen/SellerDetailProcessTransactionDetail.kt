package com.example.beefy.ui.seller.sellerdetailtransactionscreen.sellerdetailprocesstransactionscreen

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
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailOrderResponse
import com.example.beefy.databinding.FragmentSellerDetailProcessTransactionDetailBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class SellerDetailProcessTransactionDetail : Fragment() {
    private var _binding : FragmentSellerDetailProcessTransactionDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var idPembeli: String
    private lateinit var idPembayaran: String
    private lateinit var gambar:String

    private lateinit var myId:String
    private lateinit var idAkunPembeli : String
    private lateinit var namaAkunPembeli : String

    private val sellerDetailProcessTransactionViewModel : SellerDetailProcessTransactionViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idPembeli = requireArguments().getString("idPembeli").toString()
        idPembayaran = requireArguments().getString("idPembayaran").toString()
        gambar = requireArguments().getString("gambar").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerDetailProcessTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupObserver()
        setupButton()
    }

    private fun initView(){
        sellerDetailProcessTransactionViewModel.getBuyerDetail(idPembeli.toInt())
        sellerDetailProcessTransactionViewModel.getDetailOrder(idPembayaran.toInt())
    }

    private fun setupButton(){
        binding.sellerDetailProcessChatFAB.setOnClickListener {
            val bundle = Bundle().apply {
                putString("currentUserId", myId)
                putString("otherUserId", idAkunPembeli)
                putString("namaAkunPembeli", namaAkunPembeli)
            }

            findNavController().navigate(R.id.action_sellerDetailProcessTransactionDetail_to_sellerChatScreen, bundle)
        }
    }

    private fun setupObserver(){
        sellerDetailProcessTransactionViewModel.userId.observe(viewLifecycleOwner){
            myId = it
        }

        sellerDetailProcessTransactionViewModel.buyerDetail.observe(viewLifecycleOwner){
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
        sellerDetailProcessTransactionViewModel.detailOrder.observe(viewLifecycleOwner){
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
        binding.sellerDetailProcessTransactionItemCard.meatDatePriceCardNoteItemPriceTv.text = detailOrderResponse.DetailRincian?.totalHarga.toString()
        binding.sellerDetailProcessTransactionItemCard.meatDatePriceNoteCardItem.text = detailOrderResponse.Barang?.catatan
        binding.sellerDetailProcessTransactionItemCard.meatDatePriceNoteCardDateTv.text = detailOrderResponse.Barang?.tanggalPesanan
        Glide.with(binding.root).load(gambar).into(binding.sellerDetailProcessTransactionItemCard.meatDatePriceCardItemImageView)
        binding.sellerDetailProcessTransactionItemCard.meatDatePriceNoteCardItemCountTv.text = detailOrderResponse.Barang?.totalBarang.toString()
        binding.sellerDetailProcessTransactionItemCard.meatDatePriceNoteItemTitleTv.text = detailOrderResponse.Barang?.namaBarang

        Glide.with(binding.root).load(detailOrderResponse.BuktiPembayaran).into(binding.sellerDetailProcessTransactionPaymentProofImageView)
    }

    private fun setupView(buyerDetail : DetailBuyerResponse){
        binding.sellerDetailProcessTransactionAddressCard.addressSellerViewAddressTv.text = buyerDetail.alamatLengkap
        binding.sellerDetailProcessTransactionAddressCard.addressSellerViewNameTv.text = buyerDetail.namaPenerima
        binding.sellerDetailProcessTransactionAddressCard.addressSellerViewPhoneNumberTv.text = buyerDetail.nomorTelp

        namaAkunPembeli = buyerDetail.nama.toString()
        idAkunPembeli = buyerDetail.userAccount?.idAccount.toString()
    }

    private fun setLoading(boolean: Boolean) {
        if (boolean) {
            binding.sellerDetailProcessTransactionLinearLayout.loadSkeleton()
        } else {
            binding.sellerDetailProcessTransactionLinearLayout.hideSkeleton()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}