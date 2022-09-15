package com.nasaapp.kissyoursky.home.domain.model

import androidx.annotation.Keep

@Keep
data class AstronomyDetails(
    val date: String,
    val explanation: String,
    val hdImageUrl: String,
    val mediaType: String,
    val title: String,
)
