package com.example.beefy.ui.auth.registerbuyerscreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentRegisterBuyerBinding


class RegisterBuyerScreen : Fragment() {

    private var _binding: FragmentRegisterBuyerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBuyerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkEmptyField()
        validateInput()
        setupButton()
    }

    private fun setupButton() {
        //todo
        binding.registerBuyerAlrAccBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerBuyerScreen_to_loginScreenFragment)
        }

        binding.registerBuyerRegisterBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerBuyerScreen_to_registerBuyerInfoScreen)
        }
    }

    private fun validateInput() {
        binding.registerBuyerNameTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerNameTIL.error = if (s.toString().isEmpty()) {
                    "Nama tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerEmailTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerEmailTIL.error =
                    if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                        "Format Email tidak valid"
                    } else {
                        null
                    }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerPassworsTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerPasswordTIL.error = if (s.toString().length < 8) {
                    "Password tidak boleh kurang dari 8 karakter"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })


    }


    private fun checkEmptyField() {
        binding.registerBuyerRegisterBtn.isEnabled =
            binding.registerBuyerNameTIET.text.toString().isNotEmpty() &&
            binding.registerBuyerEmailTIET.text.toString().isNotEmpty() &&
            binding.registerBuyerPassworsTIET.text.toString().isNotEmpty() &&
                    binding.registerBuyerNameTIL.error == null &&
                    binding.registerBuyerEmailTIL.error == null &&
                    binding.registerBuyerPasswordTIL.error == null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}