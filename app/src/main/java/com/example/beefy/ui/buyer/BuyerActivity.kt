package com.example.beefy.ui.buyer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.beefy.R
import com.example.beefy.databinding.ActivityBuyerBinding

class BuyerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBuyerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.buyerFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.buyerBottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if(destination.id == R.id.buyer_home_screen || destination.id == R.id.buyer_scan_screen || destination.id == R.id.buyer_profile_screen || destination.id == R.id.buyerChatsListScreen){
                binding.buyerBottomNavigationView.visibility = View.VISIBLE
            }else{
                binding.buyerBottomNavigationView.visibility = View.GONE
            }
        }
    }
}