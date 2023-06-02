package com.example.beefy.ui.seller.sellerhomescreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerHomeScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class SellerHomeScreen : Fragment() {

    private var _binding : FragmentSellerHomeScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SellerHomeAdapter

    private val sellerHomeViewModel : SellerHomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupButton()
        setupObserver()


    }

    private fun setupObserver(){
        sellerHomeViewModel.product.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.sellerHomeRv.loadSkeleton(R.layout.meat_card_item){
                        itemCount(4)
                    }
                }

                is Resource.Error -> {
                    binding.sellerHomeRv.hideSkeleton()
                    Log.e(TAG, "getAllProduct SellerHomeScreen "+ it.error, )
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    binding.sellerHomeRv.hideSkeleton()
                    adapter.setData(it.data)
                }
            }
        }
    }

    private fun setupAdapter(){
        binding.sellerHomeRv.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = SellerHomeAdapter{
            val bundle = Bundle()
            bundle.putInt("idItem", it.pk.toString().toInt())
            bundle.putString("namaBarang", it.namaBarang)
            bundle.putString("deskripsi", it.deskripsi)
            bundle.putString("harga", it.harga.toString())
            findNavController().navigate(R.id.action_sellerHomeScreen_to_sellerDetailItemScreen, bundle)
        }
        binding.sellerHomeRv.adapter = adapter
    }

    private fun setupButton(){
        binding.sellerHomeAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_sellerHomeScreen_to_sellerAddItemScreen)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}