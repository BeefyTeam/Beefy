package com.example.beefy.ui.auth.splashscreen

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.beefy.R
import com.example.beefy.data.source.local.UserPreference
import com.example.beefy.databinding.ActivitySplashScreenBinding
import com.example.beefy.ui.auth.AuthActivity
import com.example.beefy.ui.buyer.BuyerActivity
import com.example.beefy.ui.seller.SellerActivity
import org.koin.android.ext.android.inject

class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    private val splashScreenViewModel : SplashScreenViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition { true }

        splashScreenViewModel.getUserType().observe(this){
            if(it.isNullOrEmpty()){
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }else{
                if(it.equals("pembeli")){
                    startActivity(Intent(this, BuyerActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, SellerActivity::class.java))
                    finish()
                }

            }
        }


    }
}