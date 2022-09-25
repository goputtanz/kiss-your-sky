package com.nasaapp.kissyoursky.home.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nasaapp.kissyoursky.home.data.repository.AstronomyRepositoryImpl
import com.nasaapp.kissyoursky.home.domain.repository.AstronomyRepository
import com.nasaapp.kissyoursky.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val _homeState = Channel<HomeState>()
    val homeState = _homeState.receiveAsFlow()

    private val repository:AstronomyRepository = AstronomyRepositoryImpl()

    init {
        getAstronomyPicture()
    }


    private fun getAstronomyPicture() {
        viewModelScope.launch {
            repository.astronomyDetails().collectLatest {
                when (it) {
                    is Resource.Loading -> _homeState.send(HomeState.Loading)
                    is Resource.Success -> _homeState.send(HomeState.Success(astronomyDetails = it.value))
                    is Resource.Error -> _homeState.send(HomeState.Error(it.error))
                }
            }
        }
    }

}