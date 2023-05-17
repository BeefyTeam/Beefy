package com.example.beefy.ui.buyer.buyerhomescreen

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerHomeScreenBinding
import com.google.android.material.carousel.CarouselLayoutManager


class BuyerHomeScreen : Fragment() {

    private var _binding : FragmentBuyerHomeScreenBinding? = null
    private val binding get() = _binding!!


    var sampleImages = intArrayOf(
        R.drawable.logo,
    )
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

        imageList.add(SlideModel("https://bit.ly/2YoJ77H", ))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", ))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", ))

        binding.buyerHomeScreenTopImageSlider.setImageList(imageList, ScaleTypes.FIT)



        binding.buyerHomeScreenSearchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        val imgList = ArrayList<String>()
        imgList.add("https://bit.ly/2YoJ77H")
        imgList.add("https://bit.ly/2BteuF2")
        imgList.add("https://bit.ly/3fLJf72")
        imgList.add("https://bit.ly/2YoJ77H")
        imgList.add("https://bit.ly/2YoJ77H")

        binding.buyerHomeScreenBestMeatRv.layoutManager = CarouselLayoutManager()
        val bestMeatAdapter = BuyerHomeScreenBestMeatAdapter(imgList){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.buyerHomeScreenBestMeatRv.adapter = bestMeatAdapter

        val storeName = ArrayList<String>()
        storeName.add("Anugrah Bersama")
        storeName.add("Budidaya daging")
        storeName.add("Daging Bersatu")
        binding.buyerHomeScreenBestStoreRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val checkStoreAdapter = BuyerHomeScreenCheckStoreAdapter(storeName){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.buyerHomeScreenBestStoreRv.adapter = checkStoreAdapter


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}