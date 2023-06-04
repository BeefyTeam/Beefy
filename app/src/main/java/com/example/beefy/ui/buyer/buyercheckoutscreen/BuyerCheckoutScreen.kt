package com.example.beefy.ui.buyer.buyercheckoutscreen

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.databinding.FragmentBuyerCheckoutScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random


class BuyerCheckoutScreen : Fragment() {

    private var _binding : FragmentBuyerCheckoutScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idToko:String
    private lateinit var idBarang:String
    private lateinit var namaBarang:String
    private lateinit var harga:String
    private lateinit var totalItem:String
    private lateinit var rekening:String

    private lateinit var priceAmount : String
    private lateinit var biayaPengiriman : String
    private lateinit var kodeUnik : String
    private lateinit var totalPembayaran : String



    private val buyerCheckoutViewModel : BuyerCheckoutViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idToko = requireArguments().getString("idToko").toString()
        idBarang = requireArguments().getString("idBarang").toString()
        namaBarang = requireArguments().getString("namaBarang").toString()
        harga = requireArguments().getString("harga").toString()
        totalItem = requireArguments().getString("totalItem").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerCheckoutScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupPriceView()
        setupObserver()
        setupButton()

    }

    private fun initView(){
        buyerCheckoutViewModel.getBuyerDetail()
        buyerCheckoutViewModel.getSellerDetail(idToko.toInt())
    }

    private fun setupBuyerView(detailBuyerResponse: DetailBuyerResponse){
        binding.buyerCheckoutShippingAddressCard.addressCardItemLabelTv.text = detailBuyerResponse.labelAlamat
        binding.buyerCheckoutShippingAddressCard.addressCardItemAddressTv.text = detailBuyerResponse.alamatLengkap
        binding.buyerCheckoutShippingAddressCard.addressCardItemNameTv.text = detailBuyerResponse.namaPenerima
        binding.buyerCheckoutShippingAddressCard.addressCardItemPhoneNumberTv.text = detailBuyerResponse.nomorTelp
    }

    private fun setupSellerView(detailPenjualResponse: DetailPenjualResponse){
        binding.buyerCheckoutStoreNameTv.text = detailPenjualResponse.namaToko
        binding.buyerCheckoutPaymentMethodCard.paymentMethodCardItemTitleTv.text = detailPenjualResponse.metodePembayaran
        rekening = detailPenjualResponse.rekening.toString()
    }
    private fun setupPriceView(){
        priceAmount = (harga.toInt() * totalItem.toInt()).toString()
        biayaPengiriman = "1000"
        kodeUnik = (0..999).random().toString()
        totalPembayaran = (priceAmount.toInt() + biayaPengiriman.toInt() + kodeUnik.toInt()).toString()

        binding.buyerCheckoutItemCard.checkoutCardItemTitleTv.text = namaBarang
        binding.buyerCheckoutItemCard.checkoutCardItemPriceTv.text = harga
        binding.buyerCheckoutItemCard.checkoutCardItemCountTv.text = totalItem

        binding.buyerCheckoutPaymentDetailCard.paymentDetailCardTotalPriceAmountTv.text = "Rp" + priceAmount.toString()
        binding.buyerCheckoutPaymentDetailCard.paymentDetailCardShippingPriceAmountTv.text = "Rp" + biayaPengiriman.toString()
        binding.buyerCheckoutPaymentDetailCard.paymentDetailCardUniquePriceTv.text = "Rp" + kodeUnik.toString()
        binding.buyerCheckoutPaymentDetailCard.paymentDetailCardTotalPaymentPriceAmountTv.text = "Rp" + totalPembayaran.toString()

        binding.buyerCheckoutTotalPriceAmountTv.text = "Rp" + totalPembayaran.toString()
    }
    private fun setupObserver(){
        buyerCheckoutViewModel.buyerDetail.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    setLoading(false)
                    Log.e(ContentValues.TAG, "buyercheckoutbuyerdetail setupObserver: "+ it.error, )
                }

                is Resource.Loading-> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupBuyerView(it.data)
                }

            }
        }

        buyerCheckoutViewModel.sellerDetail.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    setLoading(false)
                    Log.e(ContentValues.TAG, "buyercheckoutsellerdetail setupObserver: "+ it.error, )
                }

                is Resource.Loading-> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupSellerView(it.data)
                }

            }
        }

        buyerCheckoutViewModel.orderResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    binding.buyerCheckoutProgressBar.visibility = View.GONE
                    Log.e(ContentValues.TAG, "buyercheckoutorderresponse setupObserver: "+ it.error, )
                }

                is Resource.Loading-> {
                    binding.buyerCheckoutProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    val bundle = Bundle().apply {
                        putString("idOrder", it.data.idOrder.toString())
                        putString("bank", it.data.dataPembayaran?.bank)
                        putString("nomorRekening",it.data.dataPembayaran?.nomorRekening)
                        putString("atasNama", it.data.dataPembayaran?.atasNama)
                        putString("totalPembayaran", it.data.dataPembayaran?.totalPembayaran.toString())
                    }



                    findNavController().navigate(R.id.action_buyerCheckoutScreen_to_buyerUploadPaymentProofScreen, bundle)
                }

            }
        }
    }

    private fun setupButton(){
        binding.buyerCheckoutPayBtn.setOnClickListener {
            buyerCheckoutViewModel.newOrder(
                idToko.toInt(),
                idBarang.toInt(),
                rekening,
                binding.buyerCheckoutItemNoteTIET.text.toString(),
                binding.buyerCheckoutShippingAddressCard.addressCardItemAddressTv.text.toString(),
                binding.buyerCheckoutPaymentMethodCard.paymentMethodCardItemTitleTv.text.toString(),
                biayaPengiriman.toInt(),
                totalPembayaran.toInt(),
                kodeUnik.toInt(),
                totalItem.toInt()
            )
        }
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerCheckoutLinearLayout.loadSkeleton()
        } else {
            binding.buyerCheckoutLinearLayout.hideSkeleton()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}