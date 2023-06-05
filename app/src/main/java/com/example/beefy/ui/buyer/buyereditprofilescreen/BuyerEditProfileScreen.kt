package com.example.beefy.ui.buyer.buyereditprofilescreen

import android.app.Activity
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.databinding.FragmentBuyerEditProfileScreenBinding
import com.example.beefy.utils.Resource
import com.example.beefy.utils.downloadImageAsTempFile
import com.example.beefy.utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class BuyerEditProfileScreen : Fragment() {

    private var _binding : FragmentBuyerEditProfileScreenBinding? = null
    private val binding get() = _binding!!

    private var uri : Uri? = null
    private var getFile: File? = null

    private val buyerEditProfileViewModel : BuyerEditProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerEditProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buyerEditProfileViewModel.getUserProfile()

        validateInput()
        setupObserver()
        setupButton()

    }

    private fun setupObserver(){

        buyerEditProfileViewModel.userProfile.observe(viewLifecycleOwner){
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
                    Log.e(ContentValues.TAG, "buyereditprofile setupobserver: " + it.error, )
                }
            }
        }

    }

    private fun setupButton(){
        binding.buyerEditProfileUploadProfilePictureUploadBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.buyerEditProfileInfoConfirmEditBtn.setOnClickListener {
            val file = getFile as File
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            buyerEditProfileViewModel.confirmEditProfile(
                imagePart,
                binding.buyerEditProfileInfoFullAddressTIET.text.toString(),
                binding.buyerEditProfileInfoNameTIET.text.toString(),
                binding.buyerEditProfileInfoPhoneNumberTIET.text.toString(),
                binding.buyerEditProfileInfoAddressLabelTIET.text.toString(),
                binding.buyerEditProfileMyAccountNameTIET.text.toString()
            )
            findNavController().navigate(R.id.action_buyerEditProfileScreen_to_buyer_home_screen)
        }
    }


    private fun setupView(buyerResponse: DetailBuyerResponse){
        val imgUrl = buyerResponse.photoProfile.toString()

        lifecycleScope.launch {
            getFile = downloadImageAsTempFile(requireContext(), imgUrl)
        }

        Glide.with(binding.root).load(imgUrl).into(binding.buyerEditProfileUploadProfilePictureImageView)
        binding.buyerEditProfileInfoFullAddressTIET.setText(buyerResponse.alamatLengkap)
        binding.buyerEditProfileInfoNameTIET.setText(buyerResponse.namaPenerima)
        binding.buyerEditProfileInfoPhoneNumberTIET.setText(buyerResponse.nomorTelp)
        binding.buyerEditProfileInfoAddressLabelTIET.setText(buyerResponse.labelAlamat)
        binding.buyerEditProfileMyAccountNameTIET.setText(buyerResponse.nama)

    }


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                uri = data?.data!!

                val myFile = uriToFile(uri as Uri, requireActivity())
                getFile = myFile

                Glide.with(requireContext()).load(uri).into(binding.buyerEditProfileUploadProfilePictureImageView)

                checkEmptyField()
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun validateInput(){
        binding.buyerEditProfileMyAccountNameTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buyerEditProfileMyAccountNameTIL.error = if(s.toString().isEmpty()){
                    "Nama pengguna tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })


        binding.buyerEditProfileInfoNameTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buyerEditProfileInfoNameTIL.error = if(s.toString().isEmpty()){
                    "Nama Penerima tidak boleh kosong"
                } else {
                    null
                }

            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.buyerEditProfileInfoPhoneNumberTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buyerEditProfileInfoPhoneNumberTIL.error = if(s.toString().length<8){
                    "Nomor telefon tidak boleh kurang dari 8"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.buyerEditProfileInfoAddressLabelTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buyerEditProfileInfoAddressLabelTIL.error = if(s.toString().isEmpty()){
                    "Label alamat tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.buyerEditProfileInfoFullAddressTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.buyerEditProfileInfoFullAddressTIL.error = if(s.toString().isEmpty()){
                    "Alamat tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })
    }

    private fun checkEmptyField(){
        binding.buyerEditProfileInfoConfirmEditBtn.isEnabled =
            getFile !=null &&
            binding.buyerEditProfileInfoNameTIET.text.toString().isNotEmpty() &&
                    binding.buyerEditProfileInfoPhoneNumberTIET.text.toString().isNotEmpty() &&
                    binding.buyerEditProfileInfoAddressLabelTIET.text.toString().isNotEmpty() &&
                    binding.buyerEditProfileInfoFullAddressTIET.text.toString().isNotEmpty() &&
                    binding.buyerEditProfileInfoNameTIL.error == null &&
                    binding.buyerEditProfileInfoPhoneNumberTIL.error == null &&
                    binding.buyerEditProfileInfoAddressLabelTIL.error == null &&
                    binding.buyerEditProfileInfoFullAddressTIL.error == null
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerEditProfileLinearLayout.loadSkeleton()
        }else{
            binding.buyerEditProfileLinearLayout.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}