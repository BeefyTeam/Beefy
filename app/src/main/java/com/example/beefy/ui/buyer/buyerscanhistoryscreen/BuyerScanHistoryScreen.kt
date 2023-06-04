package com.example.beefy.ui.buyer.buyerscanhistoryscreen

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
import com.example.beefy.databinding.FragmentBuyerScanHistoryScreenBinding
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerScanHistoryScreen : Fragment() {

    private var _binding : FragmentBuyerScanHistoryScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BuyerScanHistoryAdapter

    private val buyerScanHistoryViewModel : BuyerScanHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerScanHistoryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObserver()


    }

    private fun setupObserver(){
        buyerScanHistoryViewModel.scanHistory.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "buyerscanhistory setupObserver: " + it.error, )
                }

                is Resource.Success -> {
                    setLoading(false)
                    adapter.setData(it.data)
                    binding.buyerScanHistoryTotalCheckTv.text = "Total " + it.data.size.toString() + " cek"
                }
            }
        }
    }

    private fun setupAdapter(){
        binding.buyerScanHistoryRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = BuyerScanHistoryAdapter{
            val bundle = Bundle().apply {
                putString("gambar", it.gambarUrl)
                putString("hasil", it.segar.toString())
                putString("levelKesegaran", it.levelKesegaran.toString() + "%")
                putString("jenis", it.jenis)
            }

            findNavController().navigate(R.id.action_buyerScanHistoryScreen_to_buyerScanResultScreen, bundle)
        }
        binding.buyerScanHistoryRv.adapter = adapter
    }

    private fun setLoading(boolean: Boolean){
        if (boolean){
            binding.buyerScanHistoryTotalCheckTv.loadSkeleton()
            binding.buyerScanHistoryRv.loadSkeleton(R.layout.scan_history_item){
                itemCount(4)
            }
        }else{
            binding.buyerScanHistoryTotalCheckTv.hideSkeleton()
            binding.buyerScanHistoryRv.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}