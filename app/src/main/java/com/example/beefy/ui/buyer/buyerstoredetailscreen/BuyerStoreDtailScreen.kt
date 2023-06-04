package com.example.beefy.ui.buyer.buyerstoredetailscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.databinding.FragmentBuyerStoreDtailScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerStoreDtailScreen : Fragment() {

    private var _binding : FragmentBuyerStoreDtailScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentUserId : String
    private lateinit var otherUserId : String

    private lateinit var adapter: BuyerStoreDetailAdapter

    private val buyerStoreDetailViewModel : BuyerStoreDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        otherUserId = requireArguments().getString("idToko").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerStoreDtailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()
        setupAdapter()
        setupButton()
        setupObserver()



    }

    private fun initView(){
        buyerStoreDetailViewModel.getUserDetail(otherUserId)
        buyerStoreDetailViewModel.getProductList(otherUserId)
    }

    private fun setupObserver(){

        buyerStoreDetailViewModel.userId.observe(viewLifecycleOwner){
            currentUserId = it
        }

        buyerStoreDetailViewModel.userDetail.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    setLoading(false)
                    setupView(it.data)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerstoredetail setupObserver: "+ it.error, )
                }
            }
        }

        buyerStoreDetailViewModel.productList.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    setLoading(false)
                    adapter.setData(it.data)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerstoredetail setupObserver: "+ it.error, )
                }
            }
        }
    }

    private fun setupView(detailPenjualResponse: DetailPenjualResponse){
        Glide.with(binding.root).load(detailPenjualResponse.logoToko).into(binding.buyerStoreDetailItem.storeDetailItemImageView)
        binding.buyerStoreDetailItem.storeDetailItemNameTv.text = detailPenjualResponse.namaToko
        binding.buyerStoreDetailItem.storeDetailItemLocationTv.text = detailPenjualResponse.alamatLengkap
        binding.buyerStoreDetailItem.storeDetailItemOperationalHourTv.text = detailPenjualResponse.jamOperasionalBuka + ":" + detailPenjualResponse.jamOperasionalTutup
    }

    private fun setupAdapter(){
        binding.buyerStoreDetailRv.layoutManager = GridLayoutManager(requireContext(),2)
        adapter = BuyerStoreDetailAdapter{product->
            val bundle = Bundle().apply {
                putString("idToko", product.IDTOKO.toString())
                putString("namaBarang", product.namaBarang)
                putString("idItem", product.pk.toString())
                putString("deskripsi", product.deskripsi)
                putString("harga", product.harga.toString())
            }

            findNavController().navigate(R.id.action_buyerStoreDtailScreen_to_buyerProductDetailScreen, bundle)
        }
        binding.buyerStoreDetailRv.adapter = adapter
    }

    private fun setupButton(){
        binding.buyerStoreDetailChatFAB.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("currentUserId", "4")
            bundle.putString("otherUserId", otherUserId)

            findNavController().navigate(R.id.action_buyerStoreDtailScreen_to_buyerChatScreen, bundle)
        }
    }

    private fun setLoading(boolean: Boolean){
        if (boolean){
            binding.buyerStoreDetailItem.storeDetailItemImageView.loadSkeleton()
            binding.buyerStoreDetailItem.storeDetailItemNameTv.loadSkeleton()
            binding.buyerStoreDetailItem.storeDetailItemLocationTv.loadSkeleton()
            binding.buyerStoreDetailItem.storeDetailItemOperationalHourTv.loadSkeleton()

            binding.buyerStoreDetailRv.loadSkeleton(R.layout.meat_card_item){
                itemCount(4)
            }

        }else{
            binding.buyerStoreDetailItem.storeDetailItemImageView.hideSkeleton()
            binding.buyerStoreDetailItem.storeDetailItemNameTv.hideSkeleton()
            binding.buyerStoreDetailItem.storeDetailItemLocationTv.hideSkeleton()
            binding.buyerStoreDetailItem.storeDetailItemOperationalHourTv.hideSkeleton()
            binding.buyerStoreDetailRv.hideSkeleton()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}