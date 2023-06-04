package com.example.beefy.ui.auth.registerbuyerscreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentRegisterBuyerBinding
import com.example.beefy.utils.Resource
import org.koin.android.ext.android.inject


class RegisterBuyerScreen : Fragment() {

    private var _binding: FragmentRegisterBuyerBinding? = null
    private val binding get() = _binding!!

    private val registerBuyerViewModel : RegisterBuyerViewModel by inject()
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

        setLoading(false)
        checkEmptyField()
        validateInput()
        setupObserver()
        setupButton()
    }

    private fun setupObserver(){
        registerBuyerViewModel.registerBuyer.observe(viewLifecycleOwner){
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
                    bundle.putString("id", it.data.idPembeli.toString())
                    bundle.putString("nama", binding.registerBuyerNameTIET.text.toString())

                    findNavController().navigate(R.id.action_registerBuyerScreen_to_registerBuyerUploadProfilePictureScreen, bundle)
                }
            }
        }
    }

    private fun setupButton() {
        //todo
        binding.registerBuyerAlrAccBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerBuyerScreen_to_loginScreenFragment)
        }

        binding.registerBuyerRegisterBtn.setOnClickListener {
            registerBuyerViewModel.registerBuyer(
                binding.registerBuyerNameTIET.text.toString(),
                binding.registerBuyerEmailTIET.text.toString(),
                binding.registerBuyerPassworsTIET.text.toString()
            )
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
                    if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
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

    private fun setLoading(boolean: Boolean){
        binding.registerBuyerProgressBar.visibility = if(
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