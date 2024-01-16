package com.cc221001.cc221015.Poke_Hike.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.cc221001.cc221015.Poke_Hike.service.SimpleForecast
import com.cc221001.cc221015.Poke_Hike.service.WeatherRepository
import com.cc221001.cc221015.Poke_Hike.service.dto.CurrentWeather
import com.cc221001.cc221015.Poke_Hike.service.dto.ForecastWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@SuppressLint("MissingPermission")
@HiltViewModel
class WeatherViewModel @Inject constructor (
    private val repository: WeatherRepository
) : ViewModel() {

    val weather: Flow<CurrentWeather?> = repository.getCurrentWeather()
    val forecast: Flow<List<SimpleForecast>> = repository.weatherForecast()

    fun onPermissionGranted(): Pair<Flow<CurrentWeather?>, Flow<List<SimpleForecast>>> {
        return Pair(weather, forecast)
    }
}
