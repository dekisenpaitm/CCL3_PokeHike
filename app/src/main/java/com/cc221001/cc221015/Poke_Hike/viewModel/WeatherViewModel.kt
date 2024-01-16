package com.cc221001.cc221015.Poke_Hike.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.cc221001.cc221015.Poke_Hike.service.WeatherRepository
import com.cc221001.cc221015.Poke_Hike.service.dto.CurrentWeather
import com.cc221001.cc221015.Poke_Hike.service.dto.FullWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class WeatherViewModel @Inject constructor (
    application: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(application) {
    // Repository instance for fetching weather data

    // Flow representing the current weather data based on the device's location
    val weather: Flow<CurrentWeather?> = repository.getCurrentWeather(getApplication() as Context)
    val forecast: Flow<List<FullWeather.Daily>?> = repository.getFiveDayForecast(getApplication() as Context)


    fun onPermissionGranted(): Flow<Pair<CurrentWeather?, List<FullWeather.Daily>?>> {
        return weather.combine(forecast) { currentWeather, fiveDayForecast ->
            Pair(currentWeather, fiveDayForecast)
        }
    }
}
