package com.example.beefy.ui.seller.sellertransactionscreen.sellerwaitingtransactionscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerWaitingTransactionScreenBinding


class SellerWaitingTransactionScreen : Fragment() {

    private var _binding : FragmentSellerWaitingTransactionScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerWaitingTransactionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = ArrayList<String>()
        for (i in 0..5){
            itemList.add("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg")
        }

        binding.sellerWaitingTransactionRv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SellerWaitingTransactionAdapter(itemList){
            findNavController().navigate(R.id.action_sellerTransactionScreen_to_sellerDetailWaitingTransactionScreen)
        }
        binding.sellerWaitingTransactionRv.adapter = adapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}