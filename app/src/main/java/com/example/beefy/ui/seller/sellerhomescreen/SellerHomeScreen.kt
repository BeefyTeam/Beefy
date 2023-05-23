package com.example.beefy.ui.seller.sellerhomescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerHomeScreenBinding


class SellerHomeScreen : Fragment() {

    private var _binding : FragmentSellerHomeScreenBinding? = null
    private val binding get() = _binding!!

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

        val itemList = ArrayList<String>()
        for (i in 0..20){
            itemList.add("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg")
        }

        binding.sellerHomeRv.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = SellerHomeAdapter(itemList){
            findNavController().navigate(R.id.action_sellerHomeScreen_to_sellerDetailItemScreen)
        }
        binding.sellerHomeRv.adapter = adapter

        binding.sellerHomeAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_sellerHomeScreen_to_sellerAddItemScreen)
        }

        binding.sellerHomeAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_sellerHomeScreen_to_sellerAddItemScreen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}