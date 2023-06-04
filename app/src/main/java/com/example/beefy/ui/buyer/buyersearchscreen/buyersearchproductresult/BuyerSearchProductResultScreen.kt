package com.example.beefy.ui.buyer.buyersearchscreen.buyersearchproductresult

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerSearchProductResultScreenBinding
import com.example.beefy.ui.buyer.buyersearchscreen.BuyerSearchViewModel
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class BuyerSearchProductResultScreen : Fragment() {

    private var _binding : FragmentBuyerSearchProductResultScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BuyerSearchScreenProductResultSreenAdapter

    private val buyerSearchViewModel : BuyerSearchViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerSearchProductResultScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupAdapter()
        setupObserver()

    }

    private fun setupObserver(){

        buyerSearchViewModel.productResult.observe(viewLifecycleOwner){
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
                    Log.e(TAG, "buyersearchproductresultscreen setupObserver: "+ it.error, )
                }
            }
        }

    }
    private fun setupAdapter(){
        binding.buyerSearchProductResultScreenRv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = BuyerSearchScreenProductResultSreenAdapter{
            val bundle = Bundle()
            bundle.putString("idItem", it.pk.toString())
            bundle.putString("namaBarang", it.namaBarang)
            bundle.putString("deskripsi", it.deskripsi)
            bundle.putString("harga", it.harga.toString())
            bundle.putString("idToko", it.IDTOKO.toString())

            findNavController().navigate(R.id.action_buyerSearchScreen_to_buyerProductDetailScreen, bundle)
        }
        binding.buyerSearchProductResultScreenRv.adapter = adapter
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerSearchProductResultScreenRv.loadSkeleton(R.layout.meat_card_item){
                itemCount(4)
            }
        }else{
            binding.buyerSearchProductResultScreenRv.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    


}