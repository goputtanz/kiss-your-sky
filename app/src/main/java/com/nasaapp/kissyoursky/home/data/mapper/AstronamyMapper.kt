package com.nasaapp.kissyoursky.home.data.mapper

import com.nasaapp.kissyoursky.home.data.dto.AstronomyResponseDto
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails

fun AstronomyResponseDto.toAstronomyResponseDto():AstronomyDetails{
    val contentUrl = if (this.mediaType == "image") this.hdurl
    else this.url
    return AstronomyDetails(this.date,this.explanation,contentUrl,this.mediaType,this.title)
}

