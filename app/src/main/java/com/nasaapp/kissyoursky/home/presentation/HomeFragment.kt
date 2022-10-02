package com.nasaapp.kissyoursky.home.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.nasaapp.kissyoursky.R
import com.nasaapp.kissyoursky.databinding.FragmentHomeBinding
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var astronomyData: AstronomyDetails

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        defaults()
        observeHomeFragmentState()
        Log.d("onViewCreated", "onViewCreated: ")
    }

    private fun defaults(){
        binding.navigateToDetails.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAstronomyDetailsFragment(astronomyData))
        }
    }

    private fun observeHomeFragmentState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collectLatest {
                    handleLoading(it.loading)
                    handleError(it.error)
                    handleSuccess(it.success)
                }
            }
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
        Log.d("loading", "handleLoading: $loading")
    }

    private fun handleSuccess(astronomyDetails: AstronomyDetails?) {
        if (astronomyDetails != null) {
            handleAnimation()
            astronomyData = astronomyDetails
            if (astronomyDetails?.mediaType == "video") {
                binding.astronomicalImage.visibility = View.GONE
                handleVideo(astronomyDetails.hdImageUrl)
            } else {
                binding.videoView.visibility = View.GONE
                binding.title.text = astronomyDetails?.title
                binding.astronomicalImage.load(astronomyDetails?.hdImageUrl)
            }

        }
        Log.d("handleSuccess", "handleSuccess: $astronomyDetails")

    }

    private fun handleAnimation() {
        binding.navigateToDetails.visibility = View.VISIBLE
        val animation:Animation = AnimationUtils.loadAnimation(requireContext(),R.anim.slide)
        binding.navigateToDetails.startAnimation(animation)
    }

    private fun handleVideo(videoUrl: String) {
        val uri: Uri = Uri.parse(videoUrl)
        binding.videoView.setVideoURI(uri)
        val mediaController = MediaController(requireActivity())
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.start()
    }

    private fun handleError(error: String) {
        if (error.isNotBlank()) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }




}