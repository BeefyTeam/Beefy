package com.example.beefy.ui.buyer.buyersearchscreen.buyersearchstoreresult

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerSearchProductResultScreenBinding
import com.example.beefy.databinding.FragmentBuyerSearchStoreResultScreenBinding
import com.example.beefy.ui.buyer.buyersearchscreen.BuyerSearchViewModel
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton


class BuyerSearchStoreResultScreen : Fragment() {

    private var _binding : FragmentBuyerSearchStoreResultScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BuyerSearchStoreResultScreenAdapter

    private val buyerSearchProductResultViewModel : BuyerSearchViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerSearchStoreResultScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObserver()

    }

    private fun setupObserver(){


        buyerSearchProductResultViewModel.storeResult.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    adapter.setData(it.data)
                }

                is Resource.Error -> {
                    adapter.setData(emptyList())
                    setLoading(false)
                    Log.e(ContentValues.TAG, "buyersearchproductresultscreen setupObserver: "+ it.error, )
                }
            }
        }
    }

    private fun setupAdapter(){
        binding.buyerSearchStoreResultRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = BuyerSearchStoreResultScreenAdapter{
            val bundle = Bundle()
            bundle.putString("idToko", it.pk.toString())
            bundle.putString("currentUserId", "4")
            findNavController().navigate(R.id.action_buyerSearchScreen_to_buyerStoreDtailScreen, bundle)
        }
        binding.buyerSearchStoreResultRv.adapter = adapter
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerSearchStoreResultRv.loadSkeleton(R.layout.store_name_with_location_card_item){
                itemCount(4)
            }
        }else{
            binding.buyerSearchStoreResultRv.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}