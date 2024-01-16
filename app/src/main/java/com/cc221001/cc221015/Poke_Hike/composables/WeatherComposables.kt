package com.cc221001.cc221015.Poke_Hike.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.service.SimpleForecast
import com.cc221001.cc221015.Poke_Hike.service.dto.CurrentWeather
import com.cc221001.cc221015.Poke_Hike.service.dto.ForecastWeather
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import kotlin.math.roundToInt

@Composable
fun DisplayWeather(weatherViewModel: WeatherViewModel) {
    val weather by weatherViewModel.weather.collectAsState(null)
    val forecast by weatherViewModel.forecast.collectAsState(emptyList())
    println("Current Weather: $weather")
    println("Forecast: $forecast")
    Column(Modifier.fillMaxSize()) {
        weather?.let {
            WeatherSummary(weather = it)
            TemperatureSummary(it)
            Divider(color = Color.White)
        }
            FiveDayForecast(forecast)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF010528))
        )
    }
}

@Composable
fun WeatherSummary(weather: CurrentWeather) {
    Box {
        Image(
            painter = painterResource(id = weather.background()),
            contentDescription = "Background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            Modifier
                .padding(top = 48.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = formatTemperature(weather.main.temp), fontSize = 48.sp, color = Color.White)
            Text(
                text = weather?.weather?.first()?.main.toString(),
                fontSize = 28.sp,
                color = Color.White
            )
            Text(text = weather?.name.toString(), fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun TemperatureSummary(weather: CurrentWeather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF010528))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            weather.main?.let { main ->
                Text(text = formatTemperature(main.tempMin ?: 0.0), color = Color.White)
                Text(text = "Min", color = Color.White)
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            weather.main?.let { main ->
                Text(text = formatTemperature(main.temp ?: 0.0), color = Color.White)
                Text(text = "Now", color = Color.White)
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            weather.main?.let { main ->
                Text(text = formatTemperature(main.tempMax ?: 0.0), color = Color.White)
                Text(text = "Max", color = Color.White)
            }
        }
    }
}

@Composable
fun FiveDayForecast(forecast: List<SimpleForecast>){
    LazyColumn {
        items(forecast) { dayForecast ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(dayForecast.date.toString())
                Text(formatTemperature(dayForecast.temperature))
            }
        }
    }
}

@DrawableRes
private fun CurrentWeather.background(): Int {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> R.drawable.background_cloudy
        conditions.contains("rain", ignoreCase = true) -> R.drawable.background_rainy
        else -> R.drawable.background_sunny
    }
}

@Composable
private fun formatTemperature(temperature: Double): String {
    return stringResource(R.string.temperature_degrees, temperature.roundToInt())
}