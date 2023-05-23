package com.example.beefy.ui.auth.registerbuyerscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentRegisterBuyerInfoScreenBinding
import com.example.beefy.ui.buyer.BuyerActivity


class RegisterBuyerInfoScreen : Fragment() {

    private var _binding : FragmentRegisterBuyerInfoScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBuyerInfoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkEmptyField()
        validateInput()
        setupButton()
    }

    private fun setupButton(){
        //todo
        binding.registerBuyerInfoConfirmAddressBtn.setOnClickListener {
            Toast.makeText(requireContext(), "SUCCESS REGISTER", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registerBuyerInfoScreen_to_loginScreenFragment)
        }
    }

    private fun validateInput(){
        binding.registerBuyerInfoNameTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoNameTIL.error = if(s.toString().isEmpty()){
                    "Nama Penerima tidak boleh kosong"
                } else {
                    null
                }

            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerInfoPhoneNumberTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoPhoneNumberTIL.error = if(s.toString().isEmpty()){
                    "Nomor telefon tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerInfoAddressLabelTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoAddressLabelTIL.error = if(s.toString().isEmpty()){
                    "Label alamat tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerInfoFullAddressTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoFullAddressTIL.error = if(s.toString().isEmpty()){
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
        binding.registerBuyerInfoConfirmAddressBtn.isEnabled =
            binding.registerBuyerInfoNameTIET.text.toString().isNotEmpty() &&
                    binding.registerBuyerInfoPhoneNumberTIET.text.toString().isNotEmpty() &&
                    binding.registerBuyerInfoAddressLabelTIET.text.toString().isNotEmpty() &&
                    binding.registerBuyerInfoFullAddressTIET.text.toString().isNotEmpty() &&
                    binding.registerBuyerInfoNameTIL.error == null &&
                    binding.registerBuyerInfoPhoneNumberTIL.error == null &&
                    binding.registerBuyerInfoAddressLabelTIL.error == null &&
                    binding.registerBuyerInfoFullAddressTIL.error == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}