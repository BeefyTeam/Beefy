package com.example.beefy.ui.buyer.buyersearchscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beefy.ui.buyer.buyersearchscreen.buyersearchproductresult.BuyerSearchProductResultScreen
import com.example.beefy.ui.buyer.buyersearchscreen.buyersearchstoreresult.BuyerSearchStoreResultScreen

class BuyerSearchScreenViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    private val fragmentList =
        listOf(BuyerSearchProductResultScreen(), BuyerSearchStoreResultScreen())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}