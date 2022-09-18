package com.nasaapp.kissyoursky.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nasaapp.kissyoursky.R
import com.nasaapp.kissyoursky.databinding.FragmentHomeBinding
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    val viewModel: HomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        observeHomeFragmentState()

    }

    private fun observeHomeFragmentState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeState.collectLatest {
                    when (it) {
                        is HomeState.Loading -> handleLoading()
                        is HomeState.Error -> handleError(it.error)
                        is HomeState.Success -> handleSuccess(it.astronomyDetails)
                    }
                }
            }
        }
    }

    private fun handleSuccess(astronomyDetails: AstronomyDetails) {
        binding.title.text = astronomyDetails.title
    }

    private fun handleError(error: String) {
        Toast.makeText(requireContext(),error,Toast.LENGTH_SHORT).show()
    }

    private fun handleLoading() {

    }


}