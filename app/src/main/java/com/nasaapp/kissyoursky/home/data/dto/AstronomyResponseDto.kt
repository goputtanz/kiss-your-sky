package com.nasaapp.kissyoursky.home.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class AstronomyResponseDto(
    @Json(name = "date")
    val date: String,
    @Json(name = "explanation")
    val explanation: String,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "service_version")
    val serviceVersion: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "hdurl")
    val hdurl: String
)