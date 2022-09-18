package com.nasaapp.kissyoursky.home.data.repository

import com.nasaapp.kissyoursky.home.data.mapper.toAstronomyResponseDto
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails
import com.nasaapp.kissyoursky.home.domain.repository.AstronomyRepository
import com.nasaapp.kissyoursky.util.RetrofitInstance
import com.nasaapp.kissyoursky.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AstronomyRepositoryImpl() : AstronomyRepository {
    override fun getAstronomyDetails(): Flow<Resource<AstronomyDetails>> = flow {
        emit(Resource.Loading)
        val responseFromApiCall = RetrofitInstance.astronomyService.getAstronomyDetails()
            if ((responseFromApiCall.isSuccessful)) {
                responseFromApiCall.body()?.let {
                    emit(Resource.Success(it.toAstronomyResponseDto()))
                }
            } else {
                emit(
                    Resource.Error(
                        responseFromApiCall.message()
                            ?: "Server Error"
                    )
                )
            }
        }
}