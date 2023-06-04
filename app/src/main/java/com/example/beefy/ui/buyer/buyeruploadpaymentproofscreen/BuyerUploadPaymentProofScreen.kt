package com.example.beefy.ui.buyer.buyeruploadpaymentproofscreen

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerUploadPaymentProofScreenBinding
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


class BuyerUploadPaymentProofScreen : Fragment() {

    private var _binding : FragmentBuyerUploadPaymentProofScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idOrder:String
    private lateinit var bank:String
    private lateinit var atasNama:String
    private lateinit var nomorRekening:String
    private lateinit var totalPembayaran:String

    private var uri : Uri? = null
    private var getFile : File? = null

    private val buyerUploadPaymentProofViewModel : BuyerUploadPaymentProofViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idOrder = requireArguments().getString("idOrder").toString()
        bank = requireArguments().getString("bank").toString()
        atasNama = requireArguments().getString("atasNama").toString()
        nomorRekening = requireArguments().getString("nomorRekening").toString()
        totalPembayaran = requireArguments().getString("totalPembayaran").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerUploadPaymentProofScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uriCheck()
        imageViewVisibility()

        setupView()
        setupObserver()
        setupButton()


    }

    private fun setupObserver(){
        buyerUploadPaymentProofViewModel.uploadPaymentProofResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    binding.buyerUploadPaymentProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    findNavController().navigate(R.id.action_buyerUploadPaymentProofScreen_to_buyerCompletePaymentScreen)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyeruploadpaymentproof setupObserver: " + it.error, )
                }

            }
        }
    }
    private fun setupButton(){
        binding.uploadPaymentProofChooseImage.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }




        binding.uploadPaymentProomConfirmPaymentBtn.setOnClickListener {
            val file = getFile as File
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            buyerUploadPaymentProofViewModel.uploadPayment(idOrder.toRequestBody("text/plain".toMediaType()), imagePart)
        }
    }

    private fun setupView(){
        binding.buyerUploadPaymentProofPaymentCard.paymentToBankNameTv.text = bank
        binding.buyerUploadPaymentProofPaymentCard.paymentToBankNumberTv.text = nomorRekening
        binding.buyerUploadPaymentProofPaymentCard.paymentToRecipientTv.text = atasNama
        binding.buyerUploadPaymentProofPaymentCard.paymentToTotalAmount.text = totalPembayaran
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerUploadPaymentProofPaymentCard.paymentToItemConstraintLayout.loadSkeleton()
        } else {
            binding.buyerUploadPaymentProofPaymentCard.paymentToItemConstraintLayout.hideSkeleton()
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

                getFile = uriToFile(uri as Uri, requireContext())

                Glide.with(requireContext()).load(uri).into(binding.imageView)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    private fun uriCheck(){
        binding.uploadPaymentProomConfirmPaymentBtn.isEnabled = uri!=null
    }

    private fun imageViewVisibility(){
        if(uri==null){
            binding.imageView.visibility = View.GONE
        }else{
            binding.imageView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}