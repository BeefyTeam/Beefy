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
import com.example.beefy.R
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.databinding.FragmentSellerDetailProcessTransactionDetailBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class SellerDetailProcessTransactionDetail : Fragment() {
    private var _binding : FragmentSellerDetailProcessTransactionDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var idProduk: String
    private lateinit var idPembeli: String
    private lateinit var idPembayaran: String

    private val sellerDetailProcessTransactionViewModel : SellerDetailProcessTransactionViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idProduk = requireArguments().getString("idProduk").toString()
        idPembeli = requireArguments().getString("idPembeli").toString()
        idPembayaran = requireArguments().getString("idPembayaran").toString()
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
    }

    private fun initView(){
        sellerDetailProcessTransactionViewModel.getBuyerDetail(idPembeli.toInt())
    }

    private fun setupObserver(){
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
    }

    private fun setupView(buyerDetail : DetailBuyerResponse){
        binding.sellerDetailProcessTransactionAddressCard.addressSellerViewAddressTv.text = buyerDetail.alamatLengkap
        binding.sellerDetailProcessTransactionAddressCard.addressSellerViewNameTv.text = buyerDetail.namaPenerima
        binding.sellerDetailProcessTransactionAddressCard.addressSellerViewPhoneNumberTv.text = buyerDetail.nomorTelp
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