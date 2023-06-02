package com.example.beefy.ui.buyer.buyersearchscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerSearchScreenBinding
import com.example.beefy.ui.buyer.buyersearchscreen.buyersearchproductresult.BuyerSearchProductResultScreen
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BuyerSearchScreen : Fragment() {

    private var _binding : FragmentBuyerSearchScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchValue : String

    private val buyerSearchViewModel : BuyerSearchViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchValue = requireArguments().getString("searchValue").toString()
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

        binding.buyerSearchView.setQuery(searchValue,false)
        buyerSearchViewModel.setQuery(searchValue)

        binding.buyerSearchView.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                buyerSearchViewModel.setQuery(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

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