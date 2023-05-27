package com.example.beefy.ui.buyer.buyerprofilescreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerProfileScreenBinding
import com.example.beefy.ui.auth.AuthActivity
import org.koin.android.ext.android.inject


class BuyerProfileScreen : Fragment() {

    private var _binding : FragmentBuyerProfileScreenBinding? = null
    private val binding get() = _binding!!

    private val buyerProfileViewModel : BuyerProfileViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buyerProfileOrderStatusBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyer_profile_screen_to_buyerOrderStatusScreen)
        }

        binding.buyerProfileScanHistoryBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyer_profile_screen_to_buyerScanHistoryScreen)
        }

        binding.buyerProfileLogoutBtn.setOnClickListener {
            buyerProfileViewModel.clearPref()
            requireActivity().startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}