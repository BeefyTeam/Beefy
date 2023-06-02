package com.example.beefy.ui.seller.sellerdetailitemscreen

import android.content.ContentValues.TAG
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
import com.example.beefy.databinding.FragmentSellerDetailItemScreenBinding
import com.example.beefy.utils.Resource
import org.koin.android.ext.android.inject


class SellerDetailItemScreen : Fragment() {

    private var _binding : FragmentSellerDetailItemScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var idItem : String
    private lateinit var namaBarang:String
    private lateinit var harga:String
    private lateinit var deskripsi:String

    private val sellerDetailItemViewModel : SellerDetailItemViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idItem = requireArguments().getInt("idItem").toString()
        namaBarang = requireArguments().getString("namaBarang").toString()
        harga = requireArguments().getString("harga").toString()
        deskripsi = requireArguments().getString("deskripsi").toString()
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

        setupView()
        setupObserver()
        setupButton()

    }
    private fun setupView(){
        Glide.with(requireContext()).load("https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg").into(binding.sellerDetailItemImageView)
        binding.sellerDetailItemNameTv.text = namaBarang
        binding.sellerDetailItemDescTv.text = deskripsi
        binding.sellerDetailItemPriceTv.text = "Rp$harga"
    }

    private fun setupObserver(){
        sellerDetailItemViewModel.delete.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "deleteProduct: "+it.error, )
                }

                is Resource.Loading -> {

                }

                is Resource.Success ->{
                    Toast.makeText(requireContext(), "berhasil hapus", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_sellerDetailItemScreen_to_sellerHomeScreen)
                }
            }
        }
    }
    private fun setupButton(){
        binding.sellerDetailItemDeleteItemBtn.setOnClickListener {
            sellerDetailItemViewModel.deleteProduct(idItem.toInt())
        }

        binding.sellerDetailItemEditItemBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("idItem", idItem.toInt())

            findNavController().navigate(R.id.action_sellerDetailItemScreen_to_sellerEditItemScreen, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}