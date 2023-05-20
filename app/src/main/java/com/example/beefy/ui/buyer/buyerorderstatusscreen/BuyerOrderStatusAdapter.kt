package com.example.beefy.ui.buyer.buyerorderstatusscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.lifecycle.Lifecycle

import com.denzcoskun.imageslider.adapters.ViewPagerAdapter
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatuscomplete.BuyerOrderStatusCompleteScreen
import com.example.beefy.ui.buyer.buyerorderstatusscreen.buyerorderstatusonprocess.BuyerOrderStatusOnProcessScreen

class BuyerOrderStatusAdapter(fm : FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    private val fragmentList = listOf(BuyerOrderStatusOnProcessScreen(), BuyerOrderStatusCompleteScreen())
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}