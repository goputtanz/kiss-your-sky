package com.nasaapp.kissyoursky.home.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.nasaapp.kissyoursky.R
import com.nasaapp.kissyoursky.databinding.FragmentHomeBinding
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var astronomyData: AstronomyDetails
    private var mPlayer: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        defaults()
        observeHomeFragmentState()
        Log.d("onViewCreated", "onViewCreated: ")
    }

    private fun defaults() {
        binding.detailsButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAstronomyDetailsFragment(
                    astronomyData
                )
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.scrollView2.setOnScrollChangeListener(View.OnScrollChangeListener { view, i, i2, i3, i4 ->
                if (i2>0){
                    binding.detailsButton.shrink()
                }else{
                    binding.detailsButton.extend()
                }
            })
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
        } else {
            binding.progressBar.visibility = View.GONE
        }
        Log.d("loading", "handleLoading: $loading")
    }

    private fun handleSuccess(astronomyDetails: AstronomyDetails?) {
        if (astronomyDetails != null) {
            astronomyData = astronomyDetails
            if (astronomyDetails.mediaType == "image") {
                handleAnimation()
                binding.astronomicalLayout.visibility = View.VISIBLE
                binding.title.text = astronomyDetails.title
                binding.astronomicalImage.load(astronomyDetails.contentUrl) {
                    placeholder(R.drawable.ic_image_not_found)
                }
                Log.d("imageloading", "imageloading: ${astronomyDetails.contentUrl}")
            } else {
                binding.videoView.visibility = View.VISIBLE
                initPlayerWhenReady(astronomyDetails.contentUrl)
                Log.d("inside video", "inside video: ${astronomyDetails.contentUrl}")
            }

        }
        Log.d("handleSuccess", "handleSuccess: $astronomyDetails")

    }

    private fun handleAnimation() {
        binding.detailsButton.visibility = View.VISIBLE
        val animation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        binding.detailsButton.startAnimation(animation)
    }

    private fun initPlayerWhenReady(contentUrl: String) {
        mPlayer = ExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = mPlayer
        mPlayer?.playWhenReady = true
        mPlayer?.setMediaSource(buildMediaSource(contentUrl))
    }

    private fun releasePlayerWhenFinished(){
        if (mPlayer == null) {
            return
        }
        mPlayer?.release()
        mPlayer = null
    }

    private fun handleError(error: String) {
        if (error.isNotBlank()) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun buildMediaSource(videoURL:String): MediaSource {
//        sample video url for testing video
//        val videoURL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()


        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL))


        return mediaSource
    }

    override fun onStop() {
        super.onStop()
        releasePlayerWhenFinished()
    }

}