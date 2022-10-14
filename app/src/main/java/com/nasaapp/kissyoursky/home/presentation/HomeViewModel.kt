package com.nasaapp.kissyoursky.home.presentation

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nasaapp.kissyoursky.home.data.repository.AstronomyRepositoryImpl
import com.nasaapp.kissyoursky.home.domain.repository.AstronomyRepository
import com.nasaapp.kissyoursky.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.reflect.Array.get

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    private var repository: AstronomyRepository = AstronomyRepositoryImpl()

    init {
        val connectivityManager: ConnectivityManager =
            application.applicationContext.getSystemService()!!

        val isNetworkConnected: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                .isNetworkCapabilitiesValid()
        } else {
            val network = connectivityManager?.activeNetworkInfo
            network != null
        }
        if (isNetworkConnected) {
            getAstronomyPicture()
            _homeState.update { it.copy(networkInfo = true) }
        } else {
            _homeState.update { it.copy(networkInfo = false) }
        }

        Log.d("verifyAvailableNetwork", "verifyAvailableNetwork: $isNetworkConnected")
    }


    private fun getAstronomyPicture() {
        viewModelScope.launch {
            repository.astronomyDetails().collectLatest { details ->
                when (details) {
                    is Resource.Loading -> _homeState.update { it.copy(loading = true) }
                    is Resource.Success -> _homeState.update {
                        it.copy(
                            success = details.value,
                            loading = false
                        )
                    }
                    is Resource.Error -> _homeState.update {
                        it.copy(
                            error = details.error,
                            loading = false
                        )
                    }
                }
            }
        }
    }


    private fun NetworkCapabilities?.isNetworkCapabilitiesValid(): Boolean = when {
        this == null -> false
        hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) -> true
        else -> false
    }


}