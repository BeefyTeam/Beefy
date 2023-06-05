package com.example.beefy.ui.buyer.buyerprofilescreen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailBuyerResponse
import com.example.beefy.databinding.FragmentBuyerProfileScreenBinding
import com.example.beefy.ui.auth.AuthActivity
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerProfileScreen : Fragment() {

    private var _binding : FragmentBuyerProfileScreenBinding? = null
    private val binding get() = _binding!!

    private val buyerProfileViewModel : BuyerProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupButton()

    }

    private fun setupObserver(){
        buyerProfileViewModel.userProfile.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    setLoading(false)
                    setupView(it.data)
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(true)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyer profile screen setupObserver: " + it.error, )
                }

            }

        }
    }

    private fun setupView(detailBuyerResponse: DetailBuyerResponse){
        Glide.with(binding.root).load(detailBuyerResponse.photoProfile.toString()).into(binding.buyerProfileImageview)
        binding.buyerProfileNameTv.text = detailBuyerResponse.nama
        binding.buyerProfileEmailTv.text = detailBuyerResponse.userAccount?.email
    }

    private fun setupButton(){
        binding.buyerProfileEditProfileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyer_profile_screen_to_buyerEditProfileScreen)
        }

        binding.buyerProfileOrderStatusBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyer_profile_screen_to_buyerOrderStatusScreen)
        }

        binding.buyerProfileScanHistoryBtn.setOnClickListener {
            findNavController().navigate(R.id.action_buyer_profile_screen_to_buyerScanHistoryScreen)
        }

        binding.buyerProfileLogoutBtn.setOnClickListener {
            buyerProfileViewModel.clearPref()
            requireActivity().startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.buyerProfileLinearLayout.loadSkeleton()
        } else {
            binding.buyerProfileLinearLayout.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}