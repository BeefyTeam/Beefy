package com.example.beefy.ui.buyer.buyerorderstatusscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerOrderStatusScreenBinding
import com.google.android.material.tabs.TabLayoutMediator


class BuyerOrderStatusScreen : Fragment() {

    private var _binding : FragmentBuyerOrderStatusScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerOrderStatusScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BuyerOrderStatusAdapter(childFragmentManager, lifecycle)
        binding.buyerOrderStatusViewPager.adapter = adapter

        TabLayoutMediator(binding.buyerOrderStatusTabs, binding.buyerOrderStatusViewPager){tab, pos ->
            when(pos){
                0 -> {
                    tab.text = "Menunggu pembayaran"
                }
                1-> {
                    tab.text = "Menunggu Konfirmasi"
                }
                2-> {
                    tab.text = "Diproses"
                }
                3 -> {
                    tab.text = "Selesai"
                }
            }

        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}