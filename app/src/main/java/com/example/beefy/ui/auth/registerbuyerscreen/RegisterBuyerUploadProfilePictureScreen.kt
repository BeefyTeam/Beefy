package com.example.beefy.ui.auth.registerbuyerscreen

import android.app.Activity
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.beefy.R
import com.example.beefy.databinding.FragmentRegisterBuyerUploadProfilePictureScreenBinding
import com.example.beefy.utils.Resource
import com.example.beefy.utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File


class RegisterBuyerUploadProfilePictureScreen : Fragment() {

    private var _binding : FragmentRegisterBuyerUploadProfilePictureScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var id : String
    private lateinit var nama : String

    private var uri : Uri? = null
    private var getFile: File? = null

    private val registerBuyerViewModel : RegisterBuyerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = requireArguments().getString("id").toString()
        nama = requireArguments().getString("nama").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBuyerUploadProfilePictureScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLoading(false)
        uriCheck()
        imageViewVisibility()
        setupObserver()
        setupButton()

    }

    private fun setupObserver(){
        registerBuyerViewModel.registerPPBuyer.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> setLoading(true)

                is Resource.Success -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()

                    val bundle = Bundle()
                    bundle.putString("id", id)
                    bundle.putString("nama", nama)

                    findNavController().navigate(R.id.action_registerBuyerUploadProfilePictureScreen_to_registerBuyerInfoScreen, bundle)
                }
            }
        }
    }

    private fun setupButton(){
        binding.registerBuyerUploadProfilePictureUploadBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    setLoading(true)
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.registerBuyerUploadProfilePictureNextBtn.setOnClickListener {
            val file = getFile as File
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            val id = id.toRequestBody("text/plain".toMediaType())


            registerBuyerViewModel.registerPPBuyer(id,imagePart)
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

                Glide.with(requireContext()).load(uri).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setLoading(false)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setLoading(false)
                        return false

                    }

                }).into(binding.registerBuyerUploadProfilePictureImageView)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                setLoading(false)
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                setLoading(false)
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    private fun uriCheck(){
        binding.registerBuyerUploadProfilePictureNextBtn.isEnabled = uri!=null
    }

    private fun imageViewVisibility(){
        if(uri==null){
            binding.registerBuyerUploadProfilePictureImageView.visibility = View.GONE
        }else{
            binding.registerBuyerUploadProfilePictureImageView.visibility = View.VISIBLE
        }
    }

    private fun setLoading(boolean: Boolean){
        binding.registerBuyerUploadPPProgressBar.visibility = if(
            boolean
        ) {
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}