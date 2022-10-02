package com.nasaapp.kissyoursky.home.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AstronomyDetails(
    val date: String,
    val explanation: String,
    val hdImageUrl: String,
    val mediaType: String,
    val title: String,
) : Parcelable
