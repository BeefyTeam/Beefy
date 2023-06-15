package com.example.beefy.ui.auth.registerbuyerscreen

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
import com.example.beefy.utils.Resource
import org.koin.android.ext.android.inject


class RegisterBuyerInfoScreen : Fragment() {

    private var _binding: FragmentRegisterBuyerInfoScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var id: String
    private lateinit var nama: String

    private val registerBuyerViewModel: RegisterBuyerViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = requireArguments().getString("id").toString()
        nama = requireArguments().getString("nama").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBuyerInfoScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLoading(false)
        checkEmptyField()
        validateInput()
        setupObserver()
        setupButton()
    }

    private fun setupObserver() {
        registerBuyerViewModel.registerEditBuyer.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> setLoading(true)

                is Resource.Success -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerBuyerInfoScreen_to_loginScreenFragment)
                }
            }
        }
    }

    private fun setupButton() {
        binding.registerBuyerInfoConfirmAddressBtn.setOnClickListener {
            registerBuyerViewModel.registerEditBuyer(
                id,
                binding.registerBuyerInfoFullAddressTIET.text.toString(),
                binding.registerBuyerInfoNameTIET.text.toString(),
                binding.registerBuyerInfoPhoneNumberTIET.text.toString(),
                binding.registerBuyerInfoAddressLabelTIET.text.toString(),
                nama
            )
        }
    }

    private fun validateInput() {
        binding.registerBuyerInfoNameTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoNameTIL.error = if (s.toString().isEmpty()) {
                    "Nama Penerima tidak boleh kosong"
                } else {
                    null
                }

            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerInfoPhoneNumberTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoPhoneNumberTIL.error = if (s.toString().length < 8) {
                    "Nomor telefon tidak boleh kurang dari 8"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerInfoAddressLabelTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoAddressLabelTIL.error = if (s.toString().isEmpty()) {
                    "Label alamat tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerBuyerInfoFullAddressTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerBuyerInfoFullAddressTIL.error = if (s.toString().isEmpty()) {
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

    private fun checkEmptyField() {
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

    private fun setLoading(boolean: Boolean) {
        binding.registerBuyerInfoProgressBar.visibility = if (
            boolean
        ) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}