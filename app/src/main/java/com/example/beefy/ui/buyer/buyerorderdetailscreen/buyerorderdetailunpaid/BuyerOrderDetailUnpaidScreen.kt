package com.example.beefy.ui.buyer.buyerorderdetailscreen.buyerorderdetailunpaid

import android.app.Activity
import android.content.ContentValues.TAG
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
import com.example.beefy.databinding.FragmentBuyerOrderDetailUnpaidScreenBinding
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


class BuyerOrderDetailUnpaidScreen : Fragment() {

    private var _binding : FragmentBuyerOrderDetailUnpaidScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idOrder : String
    private lateinit var idToko :String
    private lateinit var imgUrl : String

    private var uri : Uri? = null
    private var getFile: File? = null

    private val buyerOrderDetailUnpaidViewModel : BuyerOrderDetailUnpaidViewModel by viewModel()

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
        _binding = FragmentBuyerOrderDetailUnpaidScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uriCheck()
        imageViewVisibility()
        initView()
        setupObserver()
        setupButton()



    }

    private fun initView(){
        buyerOrderDetailUnpaidViewModel.getOrderDetail(idOrder.toInt())
        buyerOrderDetailUnpaidViewModel.getSellerDetail(idToko.toInt())
    }

    private fun setupSellerView(detailPenjualResponse: DetailPenjualResponse){
        binding.buyerOrderDetailUnpaidStoreCard.storeNameWithLocTitleTv.text = detailPenjualResponse.namaToko
        binding.buyerOrderDetailUnpaidStoreCard.storeNameWithLocLocationTv.text = detailPenjualResponse.alamatLengkap
        Glide.with(binding.root).load(detailPenjualResponse.logoToko).into(binding.buyerOrderDetailUnpaidStoreCard.storeNameWithLocImageView)

        binding.buyerOrderDetailUnpaidPaymentToCard.paymentToBankNameTv.text = detailPenjualResponse.metodePembayaran
        binding.buyerOrderDetailUnpaidPaymentToCard.paymentToBankNumberTv.text = detailPenjualResponse.rekening
        binding.buyerOrderDetailUnpaidPaymentToCard.paymentToTotalAmount.visibility = View.GONE
        binding.buyerOrderDetailUnpaidPaymentToCard.paymentToTotalAmountTitleTv.visibility = View.GONE
    }

    private fun setupOrderView(detailOrderResponse: DetailOrderResponse){
        binding.buyerOrderDetailUnpaidItemCard.meatDatePriceNoteCardItem.text = detailOrderResponse.Barang?.catatan
        binding.buyerOrderDetailUnpaidItemCard.meatDatePriceNoteItemTitleTv.text = detailOrderResponse.Barang?.namaBarang
        Glide.with(binding.root).load(imgUrl).into(binding.buyerOrderDetailUnpaidItemCard.meatDatePriceCardItemImageView)
        binding.buyerOrderDetailUnpaidItemCard.meatDatePriceNoteCardItemCountTv.text = detailOrderResponse.Barang?.totalBarang.toString() + " Barang"
        binding.buyerOrderDetailUnpaidItemCard.meatDatePriceNoteCardDateTv.text = detailOrderResponse.Barang?.tanggalPesanan
        binding.buyerOrderDetailUnpaidItemCard.meatDatePriceCardNoteItemPriceTv.text = "Rp"+detailOrderResponse.Barang?.hargaBarang.toString()



        binding.buyerOrderDetailUnpaidPaymentDetailCard.paymentDetailCardTotalPriceAmountTv.text = "Rp"+detailOrderResponse.DetailRincian?.hargaBarang.toString()
        binding.buyerOrderDetailUnpaidPaymentDetailCard.paymentDetailCardShippingPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.biayaPengiriman.toString()
        binding.buyerOrderDetailUnpaidPaymentDetailCard.paymentDetailCardUniquePriceTv.text ="Rp"+ detailOrderResponse.DetailRincian?.kodeUnik.toString()
        binding.buyerOrderDetailUnpaidPaymentDetailCard.paymentDetailCardTotalPaymentPriceAmountTv.text ="Rp"+ detailOrderResponse.DetailRincian?.totalHarga.toString()


    }

    private fun setupObserver(){
        buyerOrderDetailUnpaidViewModel.orderDetail.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerorderunpaid setupObserver order detail: "+ it.error, )
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupOrderView(it.data)
                }
            }
        }

        buyerOrderDetailUnpaidViewModel.sellerDetail.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerorderunpaid setupObserver seller detail: "+ it.error, )
                }

                is Resource.Success -> {
                    setLoading(false)
                    setupSellerView(it.data)
                }
            }
        }

        buyerOrderDetailUnpaidViewModel.uploadPaymentProof.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    binding.buyerOrderDetailUnpaidprogressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.buyerOrderDetailUnpaidprogressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerorderunpaid setupObserver seller detail: "+ it.error, )
                }

                is Resource.Success -> {
                    binding.buyerOrderDetailUnpaidprogressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_buyerOrderDetailUnpaidScreen_to_buyer_home_screen)
                }
            }
        }



    }

    private fun setupButton(){
        binding.buyerOrderDetailUnpaidPaymentChooseImageBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.buyerOrderDetailUnpaidUploadPaymentProofOrderBtn.setOnClickListener {
            val file = getFile as File
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            val id = idOrder.toRequestBody("text/plain".toMediaType())


            buyerOrderDetailUnpaidViewModel.uploadPaymentProof(id,imagePart)
        }

        binding.buyerOrderDetailUnpaidStoreCard.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idToko", idToko)
            findNavController().navigate(R.id.action_buyerOrderDetailUnpaidScreen_to_buyerStoreDtailScreen, bundle)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                uri = data?.data!!

                uriCheck()
                imageViewVisibility()

                val myFile = uriToFile(uri as Uri, requireActivity())
                getFile = myFile

                Glide.with(requireContext()).load(uri).into(binding.buyerOrderDetailUnpaidPaymentProofImageview)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun uriCheck(){
        binding.buyerOrderDetailUnpaidUploadPaymentProofOrderBtn.isEnabled = uri!=null
    }

    private fun imageViewVisibility(){
        if(uri==null){
            binding.buyerOrderDetailUnpaidPaymentProofImageview.visibility = View.GONE
        }else{
            binding.buyerOrderDetailUnpaidPaymentProofImageview.visibility = View.VISIBLE
        }
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerOrderDetailUnpaidLinearLayout.loadSkeleton()
        }else{
            binding.buyerOrderDetailUnpaidLinearLayout.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}