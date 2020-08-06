package com.estebanposada.prueba_valid.ui.track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.estebanposada.prueba_valid.R
import com.estebanposada.prueba_valid.databinding.ActivityTrackBinding

class TrackActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}