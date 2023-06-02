package com.example.beefy.ui.buyer.buyerorderdetailscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerOrderDetailScreenBinding
import org.koin.android.ext.android.inject


class BuyerOrderDetailScreen : Fragment() {

    private var _binding : FragmentBuyerOrderDetailScreenBinding? = null
    private val binding get() = _binding!!

    private val buyerOrderDetailViewModel : BuyerOrderDetailViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerOrderDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}