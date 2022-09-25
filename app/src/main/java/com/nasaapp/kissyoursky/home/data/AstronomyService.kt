package com.nasaapp.kissyoursky.home.data

import com.nasaapp.kissyoursky.home.data.dto.AstronomyResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AstronomyService {
    @GET("planetary/apod")
    fun getAstronomyDetails(@Path("api_key") key:String):Response<AstronomyResponseDto>
}