package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuswaitingpayment

import android.content.ContentValues
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
import com.example.beefy.databinding.FragmentBuyerOrderStatusWaitingPaymentScreenBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess.BuyerOrderStatusOnProcessAdapter
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerOrderStatusWaitingPaymentScreen : Fragment() {

    private var _binding : FragmentBuyerOrderStatusWaitingPaymentScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BuyerOrderStatusUnpaidAdapter

    private val buyerOrderStatusUnpaidViewModel : BuyerOrderStatusUnpaidViewModel by viewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerOrderStatusWaitingPaymentScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupAdapter()

    }

    private fun setupObserver(){
        buyerOrderStatusUnpaidViewModel.orderList.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                    Log.e(ContentValues.TAG, "buyerunpaidorder setupObserver: ", )
                }

                is Resource.Success -> {
                    setLoading(false)
                    adapter.setData(it.data)
                }

            }

        }
    }

    private fun setupAdapter(){
        binding.buyerUnpaidOrderRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = BuyerOrderStatusUnpaidAdapter{
            val bundle = Bundle()
            bundle.putString("idOrder", it.FKOrder?.IDPEMBAYARAN.toString())
            bundle.putString("idToko", it.FKOrder?.IDTOKO.toString())
            bundle.putString("imgUrl", it.FKOrder?.IDBARANG?.gambar)
            findNavController().navigate(R.id.action_buyerOrderStatusScreen_to_buyerOrderDetailUnpaidScreen, bundle)
        }
        binding.buyerUnpaidOrderRv.adapter = adapter
    }

    private fun setLoading(boolean: Boolean){
        if (boolean){
            binding.buyerUnpaidOrderRv.loadSkeleton(R.layout.order_status_card_item){
                itemCount(4)
            }
        }else{
            binding.buyerUnpaidOrderRv.hideSkeleton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}