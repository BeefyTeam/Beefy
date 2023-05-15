package com.example.beefy.ui.auth.landingscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentLandingScreenBinding


class LandingScreenFragment : Fragment() {

    private var _binding : FragmentLandingScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()

    }

    private fun setupButton(){
        binding.landingScreenLoginBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_landingScreenFragment_to_loginScreenFragment)
        }

        binding.landingScreenRegisterBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_landingScreenFragment_to_registerConfirmationScreenFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}