package com.cc221001.cc221015.Poke_Hike.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.service.dto.WeatherResponse
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel

@Composable
fun DisplayWeather(weatherViewModel: WeatherViewModel) {
    val weather by weatherViewModel.weather.collectAsState(null)
    println("${weather}")
    Column(Modifier.fillMaxSize()) {
        weather?.let {
            WeatherSummary(weather = it)
        }
        Box(
            modifier = Modifier
                .background(Color(0xFF010528))
                .fillMaxSize()
        )
    }
}

@Composable
fun WeatherSummary(weather: WeatherResponse) {
    println("where is image")
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
            Text(text = weather?.main?.temp.toString(), fontSize = 48.sp, color = Color.White)
            Text(
                text = weather?.weather?.first()?.main.toString(),
                fontSize = 28.sp,
                color = Color.White
            )
            Text(text = weather?.name.toString(), fontSize = 18.sp, color = Color.White)
        }
    }
    Box(
        modifier = Modifier
            .background(Color(0xFF010528))
            .fillMaxSize()
    )
}

@DrawableRes
private fun WeatherResponse.background(): Int {
    val conditions = weather.first().main
    return when {
        conditions.contains("cloud", ignoreCase = true) -> R.drawable.background_cloudy
        conditions.contains("rain", ignoreCase = true) -> R.drawable.background_rainy
        else -> R.drawable.background_sunny
    }
}