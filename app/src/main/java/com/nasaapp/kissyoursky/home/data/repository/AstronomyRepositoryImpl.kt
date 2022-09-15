package com.nasaapp.kissyoursky.home.data.repository

import android.app.Application
import com.nasaapp.kissyoursky.home.data.AstronomyService
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails
import com.nasaapp.kissyoursky.home.domain.repository.AstronomyRepository
import com.nasaapp.kissyoursky.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AstronomyRepositoryImpl (private val application: Application,private val astronomyService: AstronomyService):AstronomyRepository{
    override fun getAstronomyDetails(): Flow<Resource<AstronomyDetails>> = flow{

    }
}