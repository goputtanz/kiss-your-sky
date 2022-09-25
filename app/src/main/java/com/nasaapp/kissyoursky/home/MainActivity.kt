package com.nasaapp.kissyoursky.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nasaapp.kissyoursky.R
import com.nasaapp.kissyoursky.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
    }
}