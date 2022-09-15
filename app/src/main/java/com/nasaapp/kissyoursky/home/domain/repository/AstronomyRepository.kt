package com.nasaapp.kissyoursky.home.domain.repository

import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails
import com.nasaapp.kissyoursky.util.Resource
import kotlinx.coroutines.flow.Flow

interface AstronomyRepository {
    fun getAstronomyDetails(): Flow<Resource<AstronomyDetails>>
}