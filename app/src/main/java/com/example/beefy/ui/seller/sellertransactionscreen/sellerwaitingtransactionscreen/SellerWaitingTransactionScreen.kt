package com.example.beefy.ui.seller.sellertransactionscreen.sellerwaitingtransactionscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentSellerWaitingTransactionScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.android.ext.android.inject


class SellerWaitingTransactionScreen : Fragment() {

    private var _binding : FragmentSellerWaitingTransactionScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SellerWaitingTransactionAdapter

    private val sellerWaitingTransactionViewModel : SellerWaitingTransactionViewModel by inject()

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

        setLoading(false)
        setupAdapter()
        setupObserver()



    }

    private fun setupAdapter(){
        binding.sellerWaitingTransactionRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = SellerWaitingTransactionAdapter{
            val bundle = Bundle()
            bundle.putString("idProduk", it.IDBARANG.toString())
            bundle.putString("idPembeli", it.IDPEMBELI.toString())
            bundle.putString("idPembayaran", it.IDPEMBAYARAN.toString())
            findNavController().navigate(R.id.action_sellerTransactionScreen_to_sellerDetailWaitingTransactionScreen, bundle)
        }
        binding.sellerWaitingTransactionRv.adapter = adapter
    }

    private fun setupObserver(){
        sellerWaitingTransactionViewModel.orderInWaiting.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "setupObserver Seller Waiting transaction: " + it.error, )
                }

                is Resource.Success -> {
                    setLoading(false)
                    adapter.setData(it.data)
                }
            }
        }
    }


    private fun setLoading(boolean: Boolean){
        if(boolean){
            binding.sellerWaitingTransactionRv.loadSkeleton(R.layout.order_status_card_item){
                itemCount(4)
            }
        }else {
            binding.sellerWaitingTransactionRv.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}