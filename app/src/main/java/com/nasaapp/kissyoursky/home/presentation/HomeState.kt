package com.nasaapp.kissyoursky.home.presentation

import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails


data class HomeState(
    val loading: Boolean = false,
    val success: AstronomyDetails? = null,
    val error: String = ""
)