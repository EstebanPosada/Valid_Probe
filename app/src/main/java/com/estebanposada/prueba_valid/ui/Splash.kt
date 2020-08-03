package com.estebanposada.prueba_valid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.estebanposada.prueba_valid.R
import com.estebanposada.prueba_valid.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}