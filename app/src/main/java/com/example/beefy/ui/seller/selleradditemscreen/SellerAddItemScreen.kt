package com.example.beefy.ui.seller.selleradditemscreen

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.beefy.databinding.FragmentSellerAddItemScreenBinding
import com.github.dhaval2404.imagepicker.ImagePicker


class SellerAddItemScreen : Fragment() {

    private var _binding : FragmentSellerAddItemScreenBinding? = null
    private val binding get() = _binding!!

    private var uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerAddItemScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkEmptyField()
        imageViewVisibility()
        validateInput()

        binding.sellerAddItemAddImageBtn.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForImageResult.launch(intent)
                }
        }


        binding.sellerAddItemAddBtn.setOnClickListener {
            Toast.makeText(requireContext(), "berhasil tambah", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_sellerAddItemScreen_to_sellerHomeScreen)
        }

    }

    private val startForImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                uri = data?.data!!

                checkEmptyField()
                imageViewVisibility()

                Glide.with(requireContext()).load(uri).into(binding.sellerAddItemImageView)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun checkEmptyField(){
        binding.sellerAddItemAddBtn.isEnabled =
            uri!=null &&
                    binding.sellerAddItemNameTIET.text.toString().isNotEmpty() &&
                    binding.sellerAddItemDescTIET.text.toString().isNotEmpty() &&
                    binding.sellerAddItemPriceTIET.toString().isNotEmpty() &&
                    binding.sellerAddItemNameTIL.error == null &&
                    binding.sellerAddItemDescTIL.error == null &&
                    binding.sellerAddItemPriceTIL.error == null
    }

    private fun validateInput(){
        binding.sellerAddItemNameTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerAddItemNameTIL.error = if(s.toString().isEmpty()){
                     "Nama tidak boleh kosong"
                }else{
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }
        })

        binding.sellerAddItemDescTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerAddItemDescTIL.error = if(s.toString().isEmpty()){
                    "Deskripsi tidak boleh kosong"
                }else{
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.sellerAddItemPriceTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.sellerAddItemPriceTIL.error = if(s.toString().isEmpty()){
                    "Harga tidak boleh kosong"
                }else{
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

        })
    }

    private fun imageViewVisibility(){
        if(uri==null){
            binding.sellerAddItemImageView.visibility = View.GONE
        }else{
            binding.sellerAddItemImageView.visibility = View.VISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}