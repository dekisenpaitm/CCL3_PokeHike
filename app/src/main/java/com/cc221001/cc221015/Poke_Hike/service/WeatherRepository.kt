package com.cc221001.cc221015.Poke_Hike.service

import android.Manifest
import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.cc221001.cc221015.Poke_Hike.BuildConfig
import com.cc221001.cc221015.Poke_Hike.service.dto.CurrentWeather
import com.cc221001.cc221015.Poke_Hike.service.dto.ForecastWeather
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class SimpleForecast(
    val date: Date, val condition: String, val temperature: Double
)

class WeatherRepository @Inject constructor(
    private val application: Application, private val service: OpenWeatherService
) {

    private fun transform(forecast: ForecastWeather): List<SimpleForecast> {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val filtered = forecast.list.filter {
            val calendar = Calendar.getInstance().apply {
                time = Date(it.dt * 1000L) // Assuming dt is in seconds, so convert to milliseconds
            }
            calendar.get(Calendar.DAY_OF_MONTH) > today
        }

        val group = filtered.groupBy {
            Calendar.getInstance().apply {
                time = Date(it.dt * 1000L)
            }.get(Calendar.DAY_OF_MONTH)
        }

        return group.mapValues { (_, forecastList) ->
            forecastList[4]
        }.map { (_, forecast) ->
            SimpleForecast(
                Date(forecast.dt * 1000L), forecast.weather.first().main, forecast.main.temp
            )
        }
    }


    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun locationFlow() = channelFlow<Location> {
        // Fused Location Provider client for obtaining location updates
        val client = LocationServices.getFusedLocationProviderClient(application)

        // Callback to handle received location updates
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                println("Got $result")
                result.lastLocation?.let { trySend(it) }
            }
        }
        // Create a LocationRequest to define the parameters for location updates
        val request = LocationRequest.create()
            .setInterval(10_000) // Requested update interval in milliseconds
            .setFastestInterval(5_000) // Fastest possible update interval in milliseconds
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // Request high-accuracy location updates
            .setSmallestDisplacement(170f) // Minimum displacement (in meters) to trigger an update

        // Request location updates with the specified parameters
        client.requestLocationUpdates(request, callback, Looper.getMainLooper())

        // Closure to be executed when the channel is closed
        awaitClose {
            println("Closed")
            // Remove location updates when the channel is closed
            client.removeLocationUpdates(callback)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getCurrentWeather(): Flow<CurrentWeather?> {
        val flow = locationFlow().map {
            // Fetch current weather data using the obtained location
            service.getCurrentWeather(it.latitude, it.longitude, BuildConfig.API_KEY).body()
        }
        return flow
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun weatherForecast(): Flow<List<SimpleForecast>> {
        return locationFlow().map {
            service.getForecastWeather(it.latitude, it.longitude, BuildConfig.API_KEY).body()
        }.filterNotNull().map {
            transform(it)
        }

    }
}