package com.example.beefy.ui.buyer.buyerscanresultscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerScanResultScreenBinding
import kotlin.math.roundToInt


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
        when(jenis){
            "beef", "sapi" ->{
                binding.buyerScanResultResultItem.scanResultMeatResultTv.text = if(hasil.equals("fresh") || hasil.equals("true") ){
                    "Segar"
                } else {
                    "Tidak segar"
                }
                binding.buyerScanResultResultItem.scanResultFreshnessPercentageTv.text = levelKesegaran
                binding.buyerScanResultResultItem.scanResultTypeResultTv.text = jenis
                binding.buyerScanResultResultItem.scanResultShelfLifeREsult.text = checkShelfLife()
            }

            "pork", "others" ->{
                if(hasil == "others" || hasil == "false"){
                    binding.scanResultTitleTv.setText("Kami tidak mendeteksi daging pada gambar kamu")
                    binding.buyerScanResultResultItem.scanResultTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultMeatResultTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultFreshnessLEvelTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultFreshnessPercentageTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultTypeResultTv.text = "others"
                    binding.buyerScanResultResultItem.scanResultShelfLifeTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultShelfLifeREsult.visibility = View.GONE
                }else{
                    binding.buyerScanResultResultItem.scanResultTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultMeatResultTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultFreshnessLEvelTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultFreshnessPercentageTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultFreshnessPercentageTv.text = levelKesegaran
                    binding.buyerScanResultResultItem.scanResultTypeResultTv.text = jenis
                    binding.buyerScanResultResultItem.scanResultShelfLifeTv.visibility = View.GONE
                    binding.buyerScanResultResultItem.scanResultShelfLifeREsult.visibility = View.GONE
                }

            }

            else->{


            }

        }


    }

    private fun checkShelfLife():String{
        val level = levelKesegaran.dropLast(1).toFloat().roundToInt()
        return when(level){

            in 76 ..100 -> "suhu 0°C - 4°C Selama 2 - 4 hari"

            in 51 ..75 -> "suhu 0°C - 4°C Selama +- 2 hari"

            in 26 ..50 -> "suhu 0°C - 4°C dan Kurang dari 1 hari (tidak layak)"

            in 0 ..25 -> "suhu 0°C - 4°C dan Kurang dari 0 hari (tidak layak)"

            else -> "kosong"


        }

    }


}