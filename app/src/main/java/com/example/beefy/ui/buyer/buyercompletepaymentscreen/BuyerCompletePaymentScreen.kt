package com.example.beefy.ui.buyer.buyercompletepaymentscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerCompletePaymentScreenBinding


class BuyerCompletePaymentScreen : Fragment() {

    private var _binding : FragmentBuyerCompletePaymentScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerCompletePaymentScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.completePaymentOkBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyerCompletePaymentScreen_to_buyer_home_screen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}