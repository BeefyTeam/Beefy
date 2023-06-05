package com.example.beefy.ui.seller.selleredititemscreen

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
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
import com.example.beefy.databinding.FragmentSellerEditItemScreenBinding
import com.example.beefy.utils.Resource
import com.example.beefy.utils.downloadImageAsTempFile
import com.example.beefy.utils.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File


class SellerEditItemScreen : Fragment() {

    private var _binding : FragmentSellerEditItemScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idItem : String
    private lateinit var namaBarang:String
    private lateinit var harga:String
    private lateinit var deskripsi:String
    private lateinit var gambar:String

    private var uri : Uri? = null

    private var getFile : File? = null

    private val sellerEditItemViewModel : SellerEditItemViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idItem = requireArguments().getInt("idItem").toString()
        gambar = requireArguments().getString("gambar").toString()
        namaBarang = requireArguments().getString("namaBarang").toString()
        harga = requireArguments().getString("harga").toString()
        deskripsi = requireArguments().getString("deskripsi").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerEditItemScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupView()
//        imageViewVisibility()
        validateInput()
        checkEmptyField()
        setupObserver()
        setupButton()



    }

    private fun setupView(){


        val imgUrl = gambar
        lifecycleScope.launch {
            getFile = downloadImageAsTempFile(requireContext(), imgUrl)
        }
        Glide.with(binding.root).load(gambar).into(binding.sellerEditItemImageView)
        binding.sellerEditItemNameTIET.setText(namaBarang)
        binding.sellerEditItemPriceTIET.setText(harga)
        binding.sellerEditItemDescTIET.setText(deskripsi)
    }

    private fun setupObserver(){
        sellerEditItemViewModel.edit.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "editProduct: "+it.error, )
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success ->{
                    setLoading(false)
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_sellerEditItemScreen_to_sellerHomeScreen)
                }
            }
        }
    }

    private fun setupButton(){



        binding.sellerEditItemAddImageBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForImageResult.launch(intent)
                }
        }

        binding.sellerEditItemEditBtn.setOnClickListener {
            val file = getFile as File
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file_image",
                file.name,
                image
            )

            sellerEditItemViewModel.editProduct(
                idItem.toRequestBody("text/plain".toMediaType()),
                binding.sellerEditItemNameTIET.text.toString().toRequestBody("text/plain".toMediaType()),
                binding.sellerEditItemDescTIET.text.toString().toRequestBody("text/plain".toMediaType()),
                binding.sellerEditItemPriceTIET.text.toString().toRequestBody("text/plain".toMediaType()),
                imagePart
            )
        }
    }

    private val startForImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                uri = data?.data!!

//                imageViewVisibility()

                getFile = uriToFile(uri as Uri, requireContext())

                Glide.with(requireContext()).load(uri).into(binding.sellerEditItemImageView)

                checkEmptyField()

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun checkEmptyField(){
        binding.sellerEditItemEditBtn.isEnabled =
            getFile!=null &&
                    binding.sellerEditItemNameTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditItemDescTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditItemPriceTIET.text.toString().isNotEmpty() &&
                    binding.sellerEditItemNameTIL.error == null &&
                    binding.sellerEditItemDescTIL.error == null &&
                    binding.sellerEditItemPriceTIL.error == null
    }

    private fun validateInput(){
        binding.sellerEditItemNameTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditItemNameTIL.error = if(s.toString().isEmpty()){
                    "Nama tidak boleh kosong"
                }else{
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }
        })

        binding.sellerEditItemDescTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditItemDescTIL.error = if(s.toString().isEmpty()){
                    "Deskripsi tidak boleh kosong"
                }else{
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.sellerEditItemPriceTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerEditItemPriceTIL.error = if(s.toString().isEmpty()){
                    "Harga tidak boleh kosong"
                }else{
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })
    }

    private fun setLoading(boolean : Boolean){
        if(boolean){
            binding.sellerEditItemprogressBar.visibility = View.VISIBLE
        } else {
            binding.sellerEditItemprogressBar.visibility = View.GONE
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}