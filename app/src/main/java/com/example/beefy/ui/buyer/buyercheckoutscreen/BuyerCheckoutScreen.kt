package com.example.beefy.ui.buyer.buyercheckoutscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerCheckoutScreenBinding


class BuyerCheckoutScreen : Fragment() {

    private var _binding : FragmentBuyerCheckoutScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerCheckoutScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buyerCheckoutPayBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyerCheckoutScreen_to_buyerUploadPaymentProofScreen)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}