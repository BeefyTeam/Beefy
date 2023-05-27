package com.example.beefy.ui.buyer.buyerstoredetailscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerStoreDtailScreenBinding


class BuyerStoreDtailScreen : Fragment() {

    private var _binding : FragmentBuyerStoreDtailScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var otherUserId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        otherUserId = "321"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerStoreDtailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgList = ArrayList<String>()

        Glide.with(requireContext()).load("https://images.tokopedia.net/img/cache/215-square/GAnVPX/2023/2/10/c4ad6096-b419-4cb2-b0e1-0f3366950e4e.jpg").into(binding.buyerStoreDetailItem.storeDetailItemImageView)

        for(i in 0..20){
            imgList.add("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg")
        }

        binding.buyerStoreDetailRv.layoutManager = GridLayoutManager(requireContext(),2)
        val adapter = BuyerStoreDetailAdapter(imgList){
            findNavController().navigate(R.id.action_buyerStoreDtailScreen_to_buyerProductDetailScreen)
        }
        binding.buyerStoreDetailRv.adapter = adapter

        binding.buyerStoreDetailChatFAB.setOnClickListener {

            val bundle = Bundle()

            bundle.putString("otherUserId", otherUserId)

            findNavController().navigate(R.id.action_buyerStoreDtailScreen_to_buyerChatScreen, bundle)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}