package com.example.beefy.ui.buyer.buyersearchscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerSearchScreenBinding
import com.google.android.material.tabs.TabLayoutMediator


class BuyerSearchScreen : Fragment() {

    private var _binding : FragmentBuyerSearchScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerSearchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BuyerSearchScreenViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.buyerSearchTabs, binding.viewpager){tab, pos->
            when(pos){
                0 -> {
                    tab.text = "Produk"
                }
                1-> {
                    tab.text = "Toko"
                }
            }

        }.attach()


    }


}