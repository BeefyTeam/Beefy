package com.example.beefy.ui.seller.sellertransactionscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerTransactionScreenBinding
import com.google.android.material.tabs.TabLayoutMediator


class SellerTransactionScreen : Fragment() {

    private var _binding : FragmentSellerTransactionScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerTransactionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SellerTransactionAdapter(childFragmentManager, lifecycle)
        binding.sellerTransactionViewPager.adapter = adapter


        TabLayoutMediator(binding.sellerTransactionTabLayout, binding.sellerTransactionViewPager){ tab, pos ->
            when(pos){
                0 -> tab.text = "Menunggu"
                1 -> tab.text = "Diproses"
                2 -> tab.text = "Selesai"
            }

        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}