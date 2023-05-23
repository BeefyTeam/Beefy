package com.example.beefy.ui.seller.sellertransactionscreen.sellerprocesstransactionscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerProcessTransactionScreenBinding
import com.example.beefy.databinding.FragmentSellerWaitingTransactionScreenBinding
import com.example.beefy.ui.seller.sellertransactionscreen.sellerwaitingtransactionscreen.SellerWaitingTransactionAdapter


class SellerProcessTransactionScreen : Fragment() {

    private var _binding : FragmentSellerProcessTransactionScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerProcessTransactionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = ArrayList<String>()
        for (i in 0..2){
            itemList.add("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg")
        }

        binding.sellerProcessTransactionRv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SellerWaitingTransactionAdapter(itemList){
            findNavController().navigate(R.id.action_sellerTransactionScreen_to_sellerDetailProcessTransactionDetail)
        }
        binding.sellerProcessTransactionRv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}