package com.example.beefy.ui.buyer.buyerproductdetailscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.beefy.R
import com.example.beefy.databinding.FragmentBuyerProductDetailScreenBinding


class BuyerProductDetailScreen : Fragment() {

    private var _binding : FragmentBuyerProductDetailScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var otherUserId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otherUserId = "321"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerProductDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgUrl = "https://cdn.idntimes.com/content-images/post/20211202/striploin-steak-raw-beef-butchery-cut-white-table-top-view-249006-3611-90cff3e110751a704f06e897dd6e72fd.jpg"
        val storeUrl = "https://images.tokopedia.net/img/cache/215-square/GAnVPX/2023/2/10/c4ad6096-b419-4cb2-b0e1-0f3366950e4e.jpg"


        Glide.with(requireContext()).load(imgUrl).into(binding.buyerProductDetailImageView)

        Glide.with(requireContext()).load(storeUrl).into(binding.buyerProductDetailScreenStoreCard.storeNameWithLocImageView)


        var count = 1
        binding.buyerProductScreenCountTv.text = count.toString()

        binding.buyerProductDetailBotNavBarMinusBtn.isEnabled = count>1

        binding.buyerProductScreenBotNavBarCheckoutBtn.isEnabled =
            binding.buyerProductScreenCountTv.text.toString().toInt()>0

        binding.buyerProductDetailBotNavBarMinusBtn.setOnClickListener {
            count--
            binding.buyerProductScreenCountTv.text = count.toString()
            binding.buyerProductDetailBotNavBarMinusBtn.isEnabled = count>1
        }

        binding.buyerProductDetailScreenBotNavBarPlusBtn.setOnClickListener {
            count++
            binding.buyerProductScreenCountTv.text = count.toString()
            binding.buyerProductDetailBotNavBarMinusBtn.isEnabled = count>1
        }

        binding.buyerProductScreenBotNavBarCheckoutBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_buyerProductDetailScreen_to_buyerCheckoutScreen)
        }

        binding.buyerProductDetailChatBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putString("otherUserId", otherUserId)
            }


            findNavController().navigate(R.id.action_buyerProductDetailScreen_to_buyerChatScreen, bundle)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}