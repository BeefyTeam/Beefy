package com.example.beefy.ui.seller.sellerdetailitemscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerDetailItemScreenBinding


class SellerDetailItemScreen : Fragment() {

    private var _binding : FragmentSellerDetailItemScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerDetailItemScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg").into(binding.sellerDetailItemImageView)

        binding.sellerDetailItemDeleteItemBtn.setOnClickListener {
            Toast.makeText(requireContext(), "berhasil hapus", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_sellerDetailItemScreen_to_sellerHomeScreen)
        }

        binding.sellerDetailItemEditItemBtn.setOnClickListener {
            findNavController().navigate(R.id.action_sellerDetailItemScreen_to_sellerEditItemScreen)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}