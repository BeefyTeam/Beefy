package com.example.beefy.ui.auth.forgotpasswordscreen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentForgotPasswordScreenBinding
import com.example.beefy.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForgotPasswordScreen : Fragment() {

    private var _binding: FragmentForgotPasswordScreenBinding? = null
    private val binding get() = _binding!!

    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateInput()
        checkEmptyField()
        setupObserver()
        setupButton()

    }

    private fun setupButton() {
        binding.forgotPasswordConfirmBtn.setOnClickListener {
            forgotPasswordViewModel.forgetPassword(
                binding.forgotPasswordEmailTIET.text.toString(),
                binding.forgotPasswordPasswordTIET.text.toString()
            )
        }
    }

    private fun setupObserver() {
        forgotPasswordViewModel.forgetPassword.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_forgotPasswordScreen_to_loginScreenFragment)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }


            }
        }
    }

    private fun validateInput() {
        binding.forgotPasswordEmailTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.forgotPasswordEmailTIL.error =
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

        binding.forgotPasswordPasswordTIET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.forgotPasswordPasswordTIL.error = if (s.toString().length < 8) {
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
        binding.forgotPasswordConfirmBtn.isEnabled =
            binding.forgotPasswordEmailTIET.text.toString().isNotEmpty() &&
                    binding.forgotPasswordPasswordTIET.text.toString().isNotEmpty() &&
                    binding.forgotPasswordEmailTIL.error == null &&
                    binding.forgotPasswordPasswordTIL.error == null
    }

    private fun setLoading(boolean: Boolean) {
        binding.forgetPasswordprogressBar.visibility = if (
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