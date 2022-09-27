package com.nasaapp.kissyoursky.home.data.mapper

import com.nasaapp.kissyoursky.home.data.dto.AstronomyResponseDto
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails

fun AstronomyResponseDto.toAstronomyResponseDto():AstronomyDetails{
    return AstronomyDetails(this.date,this.explanation,this.url,this.mediaType,this.title)
}

