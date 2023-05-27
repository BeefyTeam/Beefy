package com.example.beefy.ui.seller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.beefy.R
import com.example.beefy.databinding.ActivitySellerBinding

class SellerActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySellerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.sellerFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.sellerBottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            if(destination.id == R.id.sellerHomeScreen|| destination.id == R.id.sellerTransactionScreen || destination.id == R.id.sellerProfileScreen || destination.id == R.id.sellerChatsListScreen){
                binding.sellerBottomNavigationView.visibility = View.VISIBLE
            }else{
                binding.sellerBottomNavigationView.visibility = View.GONE
            }
        }

    }
}