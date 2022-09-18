package com.nasaapp.kissyoursky.home.presentation

import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val astronomyDetails: AstronomyDetails) : HomeState()
    data class Error(val error: String) : HomeState()
}
