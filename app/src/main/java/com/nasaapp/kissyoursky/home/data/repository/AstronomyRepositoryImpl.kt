package com.nasaapp.kissyoursky.home.data.repository


import com.nasaapp.kissyoursky.home.data.mapper.toAstronomyResponseDto
import com.nasaapp.kissyoursky.home.domain.model.AstronomyDetails
import com.nasaapp.kissyoursky.home.domain.repository.AstronomyRepository
import com.nasaapp.kissyoursky.util.Resource
import com.nasaapp.kissyoursky.util.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AstronomyRepositoryImpl : AstronomyRepository {
    override fun astronomyDetails(): Flow<Resource<AstronomyDetails>> = flow{
        emit(Resource.Loading)
        val responseFromApiCall = RetrofitInstance.astronomyService.getAstronomyDetails("DEMO_KEY")
        if ((responseFromApiCall.isSuccessful)) {
            if (responseFromApiCall.body()!=null){
                val response = responseFromApiCall.body()?.toAstronomyResponseDto()
                emit(Resource.Success(response?:AstronomyDetails("","","","","")))
            }else{
                emit(Resource.Error(ERROR_NO_DATA))
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
    companion object{
        private const val ERROR_NO_DATA = "There is no data to display."
    }


}