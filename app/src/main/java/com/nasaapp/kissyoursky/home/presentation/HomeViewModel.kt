package com.nasaapp.kissyoursky.home.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasaapp.kissyoursky.home.data.repository.AstronomyRepositoryImpl
import com.nasaapp.kissyoursky.home.domain.repository.AstronomyRepository
import com.nasaapp.kissyoursky.util.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private var repository:AstronomyRepository = AstronomyRepositoryImpl()

    init {
        getAstronomyPicture()
    }


    private fun getAstronomyPicture() {
        viewModelScope.launch {
            repository.astronomyDetails().collectLatest {
                when (it) {
                    is Resource.Loading -> _homeState.update { it.copy(loading = true) }
                    is Resource.Success -> _homeState.update { it.copy(success = it.success) }
                    is Resource.Error -> _homeState.update { it.copy(error = it.error) }
                }
            }
        }
    }

}