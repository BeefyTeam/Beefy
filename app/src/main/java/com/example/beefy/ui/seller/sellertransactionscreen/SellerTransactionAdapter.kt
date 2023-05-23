package com.example.beefy.ui.seller.sellertransactionscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beefy.ui.seller.sellertransactionscreen.sellercompletetransactionscreen.SellerCompleteTransactionScreen
import com.example.beefy.ui.seller.sellertransactionscreen.sellerprocesstransactionscreen.SellerProcessTransactionScreen
import com.example.beefy.ui.seller.sellertransactionscreen.sellerwaitingtransactionscreen.SellerWaitingTransactionScreen

class SellerTransactionAdapter(private val fm: FragmentManager, private val lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    private val listFragment= listOf(SellerWaitingTransactionScreen(), SellerProcessTransactionScreen(), SellerCompleteTransactionScreen())
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}