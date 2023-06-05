package com.example.beefy.ui.buyer.buyerhomescreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerHomeScreenBinding
import com.example.beefy.ui.buyer.buyersearchscreen.BuyerSearchViewModel
import com.example.beefy.utils.Resource
import com.google.android.material.carousel.CarouselLayoutManager
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class BuyerHomeScreen : Fragment() {

    private var _binding: FragmentBuyerHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val buyerHomeScreenViewModel : BuyerHomeScreenViewModel by inject()

    private val buyerSearchViewModel : BuyerSearchViewModel by activityViewModel()

    private lateinit var bestMeatAdapter: BuyerHomeScreenBestMeatAdapter
    private lateinit var bestStoreAdapter: BuyerHomeScreenBestStoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupAdapter()
        setupObserver()
        setupSearchView()

    }

    private fun setupView(){
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://images.unsplash.com/photo-1607082349566-187342175e2f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"))
        imageList.add(SlideModel("https://images.unsplash.com/photo-1607083206325-caf1edba7a0f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1177&q=80"))
        imageList.add(SlideModel("https://images.unsplash.com/photo-1546502208-81d149d52bd7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1173&q=80"))

        binding.buyerHomeScreenTopImageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun setupSearchView(){
        binding.buyerHomeScreenSearchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                buyerSearchViewModel.setQuery(query.toString())
                buyerSearchViewModel.searchProduct(query.toString())
                buyerSearchViewModel.searchStore(query.toString())

                findNavController().navigate(R.id.action_buyer_home_screen_to_buyerSearchScreen)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupAdapter(){
        binding.buyerHomeScreenBestStoreRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bestStoreAdapter = BuyerHomeScreenBestStoreAdapter {
            val bundle = Bundle()
            bundle.putString("idToko", it.pk.toString())

            findNavController().navigate(R.id.action_buyer_home_screen_to_buyerStoreDtailScreen, bundle)
        }
        binding.buyerHomeScreenBestStoreRv.adapter = bestStoreAdapter



        binding.buyerHomeScreenBestMeatRv.layoutManager = CarouselLayoutManager()
        bestMeatAdapter = BuyerHomeScreenBestMeatAdapter {
            val bundle = Bundle()
            bundle.putString("idItem", it.pk.toString())
            bundle.putString("namaBarang", it.namaBarang)
            bundle.putString("deskripsi", it.deskripsi)
            bundle.putString("harga", it.harga.toString())
            bundle.putString("idToko", it.IDTOKO.toString())

            findNavController().navigate(R.id.action_buyer_home_screen_to_buyerProductDetailScreen, bundle)

        }
        binding.buyerHomeScreenBestMeatRv.adapter = bestMeatAdapter
    }

    private fun setupObserver(){
        buyerHomeScreenViewModel.trendingProduct.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    bestMeatAdapter.setData(it.data)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "trendingproduct setupObserver: " + it.error, )
                }

                is Resource.Loading -> {

                }
            }
        }

        buyerHomeScreenViewModel.trendingStore.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    bestStoreAdapter.setData(it.data)
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "trendingstore setupObserver: " + it.error, )
                }

                is Resource.Loading -> {
                }
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}