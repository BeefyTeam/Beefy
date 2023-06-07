package com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess

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
import com.example.beefy.databinding.FragmentBuyerOrderStatusOnProcessScreenBinding
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuscomplete.BuyerOrderStatusCompleteAdapter
import com.example.beefy.utils.Resource
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import org.koin.androidx.viewmodel.ext.android.viewModel


class BuyerOrderStatusOnProcessScreen : Fragment() {

    private var _binding : FragmentBuyerOrderStatusOnProcessScreenBinding? = null
    private val binding get() = _binding!!

    private val buyerOrderStatusOnProcessViewModel : BuyerOrderStatusOnProcessViewModel by viewModel()

    private lateinit var adapter: BuyerOrderStatusOnProcessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerOrderStatusOnProcessScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupAdapter()
        setupObserver()



    }

    private fun setupObserver(){
        buyerOrderStatusOnProcessViewModel.orderList.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                    Log.e(TAG, "buyerorderstatusonprocess setupObserver: ", )
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
        binding.buyerOrderStatusOnProcessRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = BuyerOrderStatusOnProcessAdapter{
            val bundle = Bundle()
            bundle.putString("idOrder", it.IDPEMBAYARAN.toString())
            bundle.putString("idToko", it.IDTOKO.toString())
            bundle.putString("imgUrl", it.IDBARANG?.gambar)
            findNavController().navigate(R.id.action_buyerOrderStatusScreen_to_buyerOrderDetailOnProcessScreen, bundle)
        }
        binding.buyerOrderStatusOnProcessRv.adapter = adapter
    }

    private fun setLoading(boolean: Boolean){
        if (boolean){
            binding.buyerOrderStatusOnProcessRv.loadSkeleton(R.layout.order_status_card_item){
                itemCount(4)
            }
        }else{
            binding.buyerOrderStatusOnProcessRv.hideSkeleton()
        }
    }

    private fun setEmptyAnim(boolean: Boolean){
        if(boolean){
            binding.buyerOrderStatusOnProcessEmptyAnim.root.visibility = View.VISIBLE
        } else{
            binding.buyerOrderStatusOnProcessEmptyAnim.root.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}