package com.example.beefy.ui.auth.loginscreen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.beefy.R
import com.example.beefy.databinding.FragmentLoginScreenBinding
import com.example.beefy.ui.buyer.BuyerActivity
import com.example.beefy.ui.seller.SellerActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject


class LoginScreenFragment : Fragment() {

    private var _binding : FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    private val loginScreenViewModel : LoginScreenViewModel by inject()

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginScreenEmailTIET.setText("a@gmail.com")
        binding.loginScreenPasswordTIET.setText("aaaaaaaa")

        checkEmptyField()
        validateInput()
        setupButton()

    }

    private fun setupButton(){
        //todo
        binding.loginScreenLoginBtn.setOnClickListener {
            val token = "123"
            val email = binding.loginScreenEmailTIET.text.toString()
            val pass = binding.loginScreenPasswordTIET.text.toString()

            loginScreenViewModel.saveToken(token)
            Toast.makeText(requireContext(), "Ke halaman pembeli", Toast.LENGTH_SHORT).show()
            requireActivity().startActivity(Intent(requireContext(), BuyerActivity::class.java))
            requireActivity().finish()




        }

        binding.loginScreenForgetPasswordBtn.setOnClickListener {
            val token = "321"
            loginScreenViewModel.saveToken(token)
            Toast.makeText(requireContext(), "Ke halaman penjual", Toast.LENGTH_SHORT).show()
            requireActivity().startActivity(Intent(requireContext(), SellerActivity::class.java))
            requireActivity().finish()
//            Toast.makeText(requireContext(), "FORGET PASSWORD", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(){
        binding.loginScreenEmailTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginScreenEmailTIL.error = if(!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    "Format Email tidak valid"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {
                checkEmptyField()
            }

        })

        binding.loginScreenPasswordTIET.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginScreenPasswordTIL.error = if(s.toString().length<8){
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
        binding.loginScreenLoginBtn.isEnabled =
            binding.loginScreenEmailTIET.text.toString().isNotEmpty() &&
                    binding.loginScreenPasswordTIET.text.toString().isNotEmpty() &&
                    binding.loginScreenEmailTIL.error == null &&
                    binding.loginScreenPasswordTIL.error == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}