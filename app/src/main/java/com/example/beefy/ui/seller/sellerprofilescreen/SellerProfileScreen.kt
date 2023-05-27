package com.example.beefy.ui.seller.sellerprofilescreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerProfileScreenBinding
import com.example.beefy.ui.auth.AuthActivity
import com.example.beefy.ui.buyer.buyerprofilescreen.BuyerProfileViewModel
import org.koin.android.ext.android.inject


class SellerProfileScreen : Fragment() {

    private var _binding : FragmentSellerProfileScreenBinding? = null
    private val binding get() = _binding!!

    private val sellerProfileViewModel : BuyerProfileViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sellerProfileLogoutBtn.setOnClickListener {
            sellerProfileViewModel.clearPref()
            requireActivity().startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }

    }


}