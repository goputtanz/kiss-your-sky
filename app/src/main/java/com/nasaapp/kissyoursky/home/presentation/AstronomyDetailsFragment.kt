package com.nasaapp.kissyoursky.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nasaapp.kissyoursky.R
import com.nasaapp.kissyoursky.databinding.FragmentAstronomyDetailsBinding


class AstronomyDetailsFragment : Fragment(R.layout.fragment_astronomy_details) {

    private lateinit var binding: FragmentAstronomyDetailsBinding
    private val args by navArgs<AstronomyDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAstronomyDetailsBinding.bind(view)


        binding.textAstronomyTitle.text = args.astronomyData.title
        binding.textAstronomyDetails.text = args.astronomyData.explanation
        binding.toolBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

}