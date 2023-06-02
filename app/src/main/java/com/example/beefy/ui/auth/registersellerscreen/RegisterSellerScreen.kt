package com.example.beefy.ui.auth.registersellerscreen

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
import com.example.beefy.databinding.FragmentRegisterSellerScreenBinding
import com.example.beefy.utils.Resource
import org.koin.android.ext.android.inject


class RegisterSellerScreen : Fragment() {

    private var _binding : FragmentRegisterSellerScreenBinding? = null
    private val binding get() = _binding!!

    private val registerSellerViewModel : RegisterSellerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterSellerScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.registerSellerStoreNameTIET.setText("a")
        binding.registerSellerEmailTIET.setText("a@gmail.co")
        binding.registerSellerPhoneNumberTIET.setText("1111111111")
        binding.registerSellerPasswordTIET.setText("aaaaaaaa")

        setLoading(false)
        checkEmptyField()
        validateInput()
        setupButton()
        setupObserver()

    }

    private fun setupObserver(){
        registerSellerViewModel.registerPenjual.observe(viewLifecycleOwner){
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
                    bundle.putString("id", it.data.idToko.toString())

                    findNavController().navigate(R.id.action_registerSellerScreen_to_registerSellerUploadProfilePictureScreen, bundle)
                }
            }
        }
    }

    private fun setupButton(){
        //todo
        binding.registerSellerRegisterBtn.setOnClickListener {
            registerSellerViewModel.registerPenjual(
                binding.registerSellerStoreNameTIET.text.toString(),
                binding.registerSellerPhoneNumberTIET.text.toString(),
                binding.registerSellerEmailTIET.text.toString(),
                binding.registerSellerPasswordTIET.text.toString()
            )

        }

        binding.registerSellerAlrHaveAccountBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerSellerScreen_to_loginScreenFragment)
        }
    }

    private fun validateInput(){
        binding.registerSellerStoreNameTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerStoreNameTIL.error = if(s.toString().isEmpty()){
                    "Nama toko tidak boleh kosong"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerSellerPhoneNumberTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerPhoneStoreNumberTIL.error = if(s.toString().length<8){
                    "Nomor telefon tidak boleh kurang dari 8 karakter"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerSellerEmailTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerEmailTIL.error = if(!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    "Format Email tidak valid"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.registerSellerPasswordTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.registerSellerPasswordTIL.error = if(s.toString().length<8){
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

    private fun checkEmptyField(){
        binding.registerSellerRegisterBtn.isEnabled =
            binding.registerSellerStoreNameTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerPhoneNumberTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerEmailTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerPasswordTIET.text.toString().isNotEmpty() &&
                    binding.registerSellerStoreNameTIL.error == null &&
                    binding.registerSellerPhoneStoreNumberTIL.error == null &&
                    binding.registerSellerEmailTIL.error == null &&
                    binding.registerSellerPasswordTIL.error == null
    }

    private fun setLoading(boolean: Boolean){
        binding.registerSellerProgressBar.visibility = if(
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