package com.cc221001.cc221015.Poke_Hike.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.cc221001.cc221015.Poke_Hike.service.WeatherRepository
import com.cc221001.cc221015.Poke_Hike.service.dto.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * ViewModel for the main application screen, responsible for managing weather-related data.
 *
 * @param application The application instance, used to access global application state.
 */
@SuppressLint("MissingPermission")
@HiltViewModel
class WeatherViewModel @Inject constructor (
    application: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(application) {
    // Repository instance for fetching weather data

    // Flow representing the current weather data based on the device's location
    val weather: Flow<WeatherResponse?> = repository.currentLocationWeather(getApplication() as Context)

    /**
     * Invoked when the necessary location permission is granted.
     * Returns a Flow representing the current weather data based on the device's location.
     *
     * @return Flow<WeatherResponse?> representing the current weather data.
     */
    fun onPermissionGranted(): Flow<WeatherResponse?> {
        return repository.currentLocationWeather(getApplication() as Context)
    }
}
