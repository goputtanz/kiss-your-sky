package com.nasaapp.kissyoursky.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nasaapp.kissyoursky.R
import com.nasaapp.kissyoursky.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    val viewModel: HomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

    }

}