package com.example.beefy.ui.auth.registersellerscreen

import android.app.Activity
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
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
import com.example.beefy.databinding.FragmentRegisterSellerUploadProfilePictureScreenBinding
import com.example.beefy.utils.Resource
import com.example.beefy.utils.reduceFileImage
import com.example.beefy.utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File


class RegisterSellerUploadProfilePictureScreen : Fragment() {

    private var _binding : FragmentRegisterSellerUploadProfilePictureScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idToko:String

    private var uri : Uri? = null
    private var getFile: File? = null

    private val registerSellerUploadPPViewModel : RegisterSellerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idToko = requireArguments().getString("id").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterSellerUploadProfilePictureScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLoading(false)
        uriCheck()
        imageViewVisibility()
        setupButton()
        setupObserver()


    }

    private fun setupObserver(){
        registerSellerUploadPPViewModel.registerAddPPPenjual.observe(viewLifecycleOwner){
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
                    bundle.putString("id", idToko)

                    findNavController().navigate(R.id.action_registerSellerUploadProfilePictureScreen_to_registerSellerInfoScreen, bundle)
                }
            }
        }
    }

    private fun setupButton(){
        binding.registerSellerUploadProfilePictureUploadBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    setLoading(true)
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.registerSellerUploadProfilePictureNextBtn.setOnClickListener {
            val file = getFile as File
            val reducedFile = reduceFileImage(file)
            val image = reducedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            val id = idToko.toRequestBody("text/plain".toMediaType())

            registerSellerUploadPPViewModel.editPPPenjual(id, imagePart)


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

                Glide.with(requireContext()).load(uri).listener(object :
                    RequestListener<Drawable> {

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

                }).into(binding.registerSellerUploadProfilePictureImageView)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                setLoading(false)
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                setLoading(false)
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    private fun uriCheck(){
        binding.registerSellerUploadProfilePictureNextBtn.isEnabled = uri!=null
    }

    private fun imageViewVisibility(){
        if(uri==null){
            binding.registerSellerUploadProfilePictureImageView.visibility = View.GONE
        }else{
            binding.registerSellerUploadProfilePictureImageView.visibility = View.VISIBLE
        }
    }

    private fun setLoading(boolean: Boolean){
        binding.registerSellerUploadPPProgressBar.visibility = if(
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