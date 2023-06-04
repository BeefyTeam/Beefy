package com.example.beefy.ui.buyer.buyerscanresultscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerScanResultScreenBinding


class BuyerScanResultScreen : Fragment() {

    private var _binding : FragmentBuyerScanResultScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var gambar : String
    private lateinit var hasil : String
    private lateinit var levelKesegaran : String
    private lateinit var jenis : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gambar = requireArguments().getString("gambar").toString()
        hasil = requireArguments().getString("hasil").toString()
        levelKesegaran = requireArguments().getString("levelKesegaran").toString()
        jenis = requireArguments().getString("jenis").toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerScanResultScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()


    }

    private fun setupView(){
        Glide.with(binding.root).load(gambar).into(binding.scanResultImageView)
        binding.buyerScanResultResultItem.scanResultMeatResultTv.text = if(hasil.equals("fresh") || hasil.equals("true") ){
            "Segar"
        } else {
            "Tidak segar"
        }
        binding.buyerScanResultResultItem.scanResultFreshnessPercentageTv.text = levelKesegaran
        binding.buyerScanResultResultItem.scanResultTypeResultTv.text = jenis
    }


}