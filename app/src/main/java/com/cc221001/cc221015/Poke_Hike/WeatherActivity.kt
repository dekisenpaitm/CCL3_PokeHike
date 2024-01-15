package com.cc221001.cc221015.Poke_Hike

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
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
import com.cc221001.cc221015.Poke_Hike.service.dto.WeatherResponse
import com.cc221001.cc221015.Poke_Hike.ui.theme.MyApplicationTheme
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

class WeatherActivity {

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {
        // This line declares and initializes a MainViewModel using the viewModels() delegate.
        // The 'by viewModels()' part indicates that the ViewModel instance will be scoped to the lifecycle of the corresponding Activity or Fragment.
        // Scoping to the lifecycle means that MainViewModel instance is created in association with the lifecycle of the activity.
        //  Useful because it means ViewModel will be automatically cleared when activity is destroyed => no memory leaks, cuz n VM beyond lifecycle
        private val weatherViewModel: WeatherViewModel by viewModels()

        // Creating a property to hold the ActivityResultLauncher for requesting a permission.
        private val requestPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                // This code block is executed when the permission request is completed.
                // If the permission is granted, call the checkLocation() function. it = isPermissionGranted
                if (it) weatherViewModel.onPermissionGranted()
            }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            println("Activity Created")
            setContent {
                MyApplicationTheme {
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
                        // Initiating the request to launch the permission dialog for accessing fine location.
                        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
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
        }
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


}