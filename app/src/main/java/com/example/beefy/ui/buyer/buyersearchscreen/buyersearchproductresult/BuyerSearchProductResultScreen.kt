package com.example.beefy.ui.buyer.buyersearchscreen.buyersearchproductresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerSearchProductResultScreenBinding


class BuyerSearchProductResultScreen : Fragment() {

    private var _binding : FragmentBuyerSearchProductResultScreenBinding? = null
    private val binding get() = _binding!!

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

        val itemList = ArrayList<String>()
        for (i in 0..20){
            itemList.add("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg")
        }


        binding.buyerSearchProductResultScreenRv.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = BuyerSearchScreenProductResultSreenAdapter(itemList){
            findNavController().navigate(R.id.action_buyerSearchScreen_to_buyerProductDetailScreen)
        }
        binding.buyerSearchProductResultScreenRv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}