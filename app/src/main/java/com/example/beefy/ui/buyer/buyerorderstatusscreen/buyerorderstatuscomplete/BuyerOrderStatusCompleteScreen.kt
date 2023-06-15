package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuscomplete

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerOrderStatusCompleteScreenBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess.BuyerOrderStatusOnProcessAdapter
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerOrderStatusCompleteScreen : Fragment() {

    private var _binding : FragmentBuyerOrderStatusCompleteScreenBinding? = null
    private val binding get() = _binding!!

    private val buyerOrderStatusCompleteViewModel : BuyerOrderStatusCompleteViewModel by viewModel()

    private lateinit var adapter: BuyerOrderStatusCompleteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerOrderStatusCompleteScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupAdapter()
        setupObserver()
    }

    private fun setupObserver(){
        buyerOrderStatusCompleteViewModel.orderList.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "buyerorderstatuscomplete setupObserver: ", )
                }

                is Resource.Success -> {
                    if(it.data.isEmpty()){
                        setEmptyAnim(true)
                    }else{
                        setEmptyAnim(false)
                    }
                    setLoading(false)
                    adapter.setData(it.data)
                }

            }

        }
    }

    private fun setupAdapter(){
        binding.buyerOrderStatusCompleteRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = BuyerOrderStatusCompleteAdapter{
            val bundle = Bundle()
            bundle.putString("idOrder", it.IDPEMBAYARAN.toString())
            bundle.putString("idToko", it.IDTOKO.toString())
            bundle.putString("imgUrl", it.IDBARANG?.gambar)

            findNavController().navigate(R.id.action_buyerOrderStatusScreen_to_buyerOrderDetailCompleteScreen, bundle)
        }
        binding.buyerOrderStatusCompleteRv.adapter = adapter
    }

    private fun setLoading(boolean: Boolean){
        if (boolean){
            binding.buyerOrderStatusCompleteRv.loadSkeleton(R.layout.order_status_card_item){
                itemCount(4)
            }
        }else{
            binding.buyerOrderStatusCompleteRv.hideSkeleton()
        }
    }

    private fun setEmptyAnim(boolean: Boolean){
        if(boolean){
            binding.buyerOrderStatusCompleteEmptyAnim.root.visibility = View.VISIBLE
        } else{
            binding.buyerOrderStatusCompleteEmptyAnim.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}