package com.example.beefy.ui.buyer.buyerhomescreen

import android.Manifest
import android.content.ContentValues.TAG
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerHomeScreenBinding
import com.example.beefy.utils.Resource
import com.google.android.material.carousel.CarouselLayoutManager
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class BuyerHomeScreen : Fragment() {

    private var _binding: FragmentBuyerHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val buyerHomeScreenViewModel : BuyerHomeScreenViewModel by inject()

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

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://images.unsplash.com/photo-1607082349566-187342175e2f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80"))
        imageList.add(SlideModel("https://images.unsplash.com/photo-1607083206325-caf1edba7a0f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1177&q=80"))
        imageList.add(SlideModel("https://images.unsplash.com/photo-1546502208-81d149d52bd7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1173&q=80"))

        binding.buyerHomeScreenTopImageSlider.setImageList(imageList, ScaleTypes.FIT)



        binding.buyerHomeScreenSearchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("searchValue", query)

                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_buyer_home_screen_to_buyerSearchScreen, bundle)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        val storeName = ArrayList<String>()
        storeName.add("Anugrah Bersama")
        storeName.add("Budidaya daging")
        storeName.add("Daging Bersatu")
        binding.buyerHomeScreenBestStoreRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val checkStoreAdapter = BuyerHomeScreenCheckStoreAdapter(storeName) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.buyerHomeScreenBestStoreRv.adapter = checkStoreAdapter

        val imgList = ArrayList<String>()
        imgList.add("https://cdn.idntimes.com/content-images/post/20211202/2xsirloin-m-041e6839f3db56849a490f059a65b743.jpg")
        imgList.add("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg")
        imgList.add("https://cdn.idntimes.com/content-images/post/20211202/featured-image-tbone-1080x675-1097f7cd916993fcfc1969fa23e7b1a2.jpg")
        imgList.add("https://cdn.idntimes.com/content-images/post/20211202/porterhouse2-ba1e93edc4eb3d376011a096c909a5f9.jpg")

        binding.buyerHomeScreenBestMeatRv.layoutManager = CarouselLayoutManager()
        val bestMeatAdapter = BuyerHomeScreenBestMeatAdapter(imgList) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.buyerHomeScreenBestMeatRv.adapter = bestMeatAdapter


        buyerHomeScreenViewModel.helloworld.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success ->{
                    Log.e(TAG, "onViewCreated: success" + it.data.message, )
                }

                is Resource.Error -> Log.e(TAG, "onViewCreated: error" + it.error, )

                is Resource.Loading -> Log.e(TAG, "onViewCreated: loading", )
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}