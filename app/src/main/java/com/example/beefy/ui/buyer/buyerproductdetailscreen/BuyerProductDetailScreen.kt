package com.example.beefy.ui.buyer.buyerproductdetailscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.databinding.FragmentBuyerProductDetailScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerProductDetailScreen : Fragment() {

    private var _binding : FragmentBuyerProductDetailScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idToko : String
    private lateinit var idItem : String
    private lateinit var namaBarang:String
    private lateinit var gambar:String
    private lateinit var harga:String
    private lateinit var deskripsi:String
    private lateinit var currentUserId : String

    private lateinit var idAkunPenjual : String
    private lateinit var namaAkunPenjual : String

    private val buyerProductDetailViewModel : BuyerProductDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idToko = requireArguments().getString("idToko").toString()
        idItem = requireArguments().getString("idItem").toString()
        namaBarang = requireArguments().getString("namaBarang").toString()
        gambar = requireArguments().getString("gambar").toString()
        harga = requireArguments().getString("harga").toString()
        deskripsi = requireArguments().getString("deskripsi").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerProductDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupObserver()
        setupButton()


    }

    private fun initView(){
        buyerProductDetailViewModel.getSellerProfile(idToko)
    }
    private fun setupObserver(){
        buyerProductDetailViewModel.userId.observe(viewLifecycleOwner){
            currentUserId = it
        }

        buyerProductDetailViewModel.sellerProfile.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    setLoading(true)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerproductdetailscreen setupObserver: "+it.error, )
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    idAkunPenjual = it.data.userAccount?.idAccount.toString()
                    namaAkunPenjual = it.data.namaToko.toString()
                    setLoading(false)
                    setupView(it.data)
                }

            }
        }
    }

    private fun setupView(detailPenjualResponse: DetailPenjualResponse){
        //item
        Glide.with(requireContext()).load(gambar).into(binding.buyerProductDetailImageView)
        binding.buyerProductScreenTitleTv.text = namaBarang
        binding.buyerProductDetailPriceTv.text = "Rp"+ harga
        binding.buyerProductDetailDescTv.text = deskripsi


        //toko
        Glide.with(binding.root).load(detailPenjualResponse.logoToko).into(binding.buyerProductDetailScreenStoreCard.storeNameWithLocImageView)
        binding.buyerProductDetailScreenStoreCard.storeNameWithLocTitleTv.setText(detailPenjualResponse.namaToko)
        binding.buyerProductDetailScreenStoreCard.storeNameWithLocLocationTv.setText(detailPenjualResponse.alamatLengkap)
    }

    private fun setupButton(){
        var count = 1
        binding.buyerProductScreenCountTv.text = count.toString()

        binding.buyerProductDetailBotNavBarMinusBtn.isEnabled = count>1

        binding.buyerProductScreenBotNavBarCheckoutBtn.isEnabled =
            binding.buyerProductScreenCountTv.text.toString().toInt()>0

        binding.buyerProductDetailBotNavBarMinusBtn.setOnClickListener {
            count--
            binding.buyerProductScreenCountTv.text = count.toString()
            binding.buyerProductDetailBotNavBarMinusBtn.isEnabled = count>1
        }

        binding.buyerProductDetailScreenBotNavBarPlusBtn.setOnClickListener {
            count++
            binding.buyerProductScreenCountTv.text = count.toString()
            binding.buyerProductDetailBotNavBarMinusBtn.isEnabled = count>1
        }

        binding.buyerProductScreenBotNavBarCheckoutBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("idToko", idToko)
                putString("totalItem", count.toString())
                putString("idBarang", idItem)
                putString("gambar", gambar)
                putString("namaBarang", namaBarang)
                putString("harga", harga)
            }
            it.findNavController().navigate(R.id.action_buyerProductDetailScreen_to_buyerCheckoutScreen, bundle)
        }

        binding.buyerProductDetailChatBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("currentUserId", currentUserId)
                putString("otherUserId", idAkunPenjual)
                putString("namaAkunPenjual", namaAkunPenjual)
            }
            findNavController().navigate(R.id.action_buyerProductDetailScreen_to_buyerChatScreen, bundle)
        }

        binding.buyerProductDetailScreenStoreCard.root.setOnClickListener {
            val bundle = Bundle().apply {
                putString("currentUserId", currentUserId)
                putString("idToko", idToko)
            }
            findNavController().navigate(R.id.action_buyerProductDetailScreen_to_buyerStoreDtailScreen, bundle)
        }
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerProductDetailLinearLayout.loadSkeleton()
        }else{
            binding.buyerProductDetailLinearLayout.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}