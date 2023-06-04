package com.example.beefy.ui.seller.sellerprofilescreen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.data.response.DetailPenjualResponse
import com.example.beefy.databinding.FragmentSellerProfileScreenBinding
import com.example.beefy.ui.auth.AuthActivity
import com.example.beefy.ui.buyer.buyerprofilescreen.BuyerProfileViewModel
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SellerProfileScreen : Fragment() {

    private var _binding : FragmentSellerProfileScreenBinding? = null
    private val binding get() = _binding!!

    private val sellerProfileViewModel : SellerProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellerProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setupButton()
        setupObserver()


    }

    private fun setupObserver(){
        sellerProfileViewModel.userProfile.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "profile view model setupObserver: " + it.error, )
                }

                is Resource.Loading -> setLoading(true)

                is Resource.Success -> {
                    setLoading(false)
                    setupView(it.data)
                }
            }
        }
    }

    private fun setupButton(){
        binding.sellerProfileEditProfileBtn.setOnClickListener {
            findNavController().navigate(R.id.action_sellerProfileScreen_to_sellerEditProfileScreen)
        }

        binding.sellerProfileLogoutBtn.setOnClickListener {
            binding.sellerProfileProgressBar.visibility = View.VISIBLE
            sellerProfileViewModel.clearPref()
            requireActivity().startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun initView(){
        sellerProfileViewModel.getDetailPenjual()
    }

    private fun setupView(detailPenjualResponse: DetailPenjualResponse){
        Glide.with(binding.root).load(detailPenjualResponse.logoToko).into(binding.sellerProfileImageview)
        binding.sellerProfileNameTv.text = detailPenjualResponse.namaToko
        binding.sellerProfileEmailTv.text = detailPenjualResponse.userAccount?.email
    }

    private fun setLoading(boolean: Boolean){
        if (boolean){
            binding.sellerProfileLinearLayout.loadSkeleton()
        }else{
            binding.sellerProfileLinearLayout.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}