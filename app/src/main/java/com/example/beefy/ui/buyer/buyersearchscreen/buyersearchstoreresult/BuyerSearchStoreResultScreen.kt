package com.example.beefy.ui.buyer.buyersearchscreen.buyersearchstoreresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerSearchProductResultScreenBinding
import com.example.beefy.databinding.FragmentBuyerSearchStoreResultScreenBinding


class BuyerSearchStoreResultScreen : Fragment() {

    private var _binding : FragmentBuyerSearchStoreResultScreenBinding? = null
    private val binding get() = _binding!!

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

        val imgList = ArrayList<String>()
        for(i in 0..20){
            imgList.add("https://images.tokopedia.net/img/cache/215-square/GAnVPX/2023/2/10/c4ad6096-b419-4cb2-b0e1-0f3366950e4e.jpg")
        }

        binding.buyerSearchStoreResultRv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BuyerSearchStoreResultScreenAdapter(imgList){
            findNavController().navigate(R.id.action_buyerSearchScreen_to_buyerStoreDtailScreen)
        }
        binding.buyerSearchStoreResultRv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}