package com.example.beefy.ui.buyer.buyerscanscreen

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
import com.example.beefy.databinding.FragmentBuyerScanScreenBinding
import com.example.beefy.utils.Resource
import com.example.beefy.utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class BuyerScanScreen : Fragment() {

    private var _binding : FragmentBuyerScanScreenBinding? = null
    private val binding get() = _binding!!

    private var uri : Uri? = null
    private var getFile: File? = null

    private val buyerScanViewModel : BuyerScanViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerScanScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uriCheck()
        imageViewVisibility()
        setupObserver()
        setupButton()

    }

    private fun setupObserver(){
        buyerScanViewModel.scanResult.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerscanscreen setupObserver: "+ it.error, )
                }

                is Resource.Success -> {
                    val bundle = Bundle().apply {
                        putString("gambar", uri.toString())
                        putString("hasil", it.data.data?.hasil)
                        putString("levelKesegaran", it.data.data?.levelKesegaran)
                        putString("jenis", it.data.data?.jenis)
                    }

                    findNavController().navigate(R.id.action_buyer_scan_screen_to_buyerScanResultScreen, bundle)

                }
            }
        }
    }

    private fun setupButton(){
        binding.buyerScanSlectPhotoBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.buyerScanBtn.setOnClickListener {
            val file = getFile as File
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            Log.e(TAG, "setupButton: scan btn clicked", )

            buyerScanViewModel.scanMeat(imagePart)
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

                Glide.with(requireContext()).load(uri).into(binding.buyerScanImageView)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun uriCheck(){
        binding.buyerScanBtn.isEnabled = uri!=null
    }

    private fun imageViewVisibility(){
        if(uri==null){
            binding.buyerScanImageView.visibility = View.GONE
        }else{
            binding.buyerScanImageView.visibility = View.VISIBLE
        }
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerScanProgressBar.visibility = View.VISIBLE
        }else{
            binding.buyerScanProgressBar.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}