package com.nasaapp.kissyoursky.home.data.mapper

import com.nasaapp.kissyoursky.home.data.dto.AstronomyResponseDto
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails

fun AstronomyResponseDto.toAstronomyResponseDto():AstronomyDetails{
    val imageURL = this.hdurl ?: this.url
    return AstronomyDetails(this.date,this.explanation,imageURL,this.mediaType,this.title)
}

