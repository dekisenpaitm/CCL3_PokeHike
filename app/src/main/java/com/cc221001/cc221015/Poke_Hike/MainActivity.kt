package com.cc221001.cc221015.Poke_Hike

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.cc221001.cc221015.Poke_Hike.composables.CreatePokeballEntries
import com.cc221001.cc221015.Poke_Hike.data.PokeballBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.PokemonBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.TrainerBaseHandler
import com.cc221001.cc221015.Poke_Hike.service.StepCounterService
import com.cc221001.cc221015.Poke_Hike.ui.theme.MyApplicationTheme
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import com.cc221001.cc221015.Poke_Hike.views.MainView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Database handler for Pokemon trainers.
    private val db = TrainerBaseHandler(this)

    // ViewModel for the main screen.
    private val mainViewModel = MainViewModel(db)

    // Database handler for Pokemon entities.
    private val pdb = PokemonBaseHandler(this)

    // ViewModel for the Pokemon-related view.
    private val pokemonViewModel = PokemonViewModel(pdb)

    // Database handler for Pokemon entities.
    private val pbdb = PokeballBaseHandler(this)

    // ViewModel for the Pokemon-related view.
    private val pokeballViewModel = PokeballViewModel(pbdb)

    private val weatherViewModel: WeatherViewModel by viewModels()
    // Creating a property to hold the ActivityResultLauncher for requesting a permission.
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            // This code block is executed when the permission request is completed.
            // If the permission is granted, call the checkLocation() function. it = isPermissionGranted
            if (it) weatherViewModel.onPermissionGranted()
        }

    private val requestPermissionStepCounter =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                // Permission granted, start the StepCounterService
                startForegroundService(Intent(this, StepCounterService::class.java))
            } else {
                // Permission denied
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check and request permission before starting the service
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            requestPermissionStepCounter.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        } else {
            // Permission is already granted, start the service
            startForegroundService(Intent(this, StepCounterService::class.java))
        }


        setContent {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialize and fetch Pokemon trainers from the database.
                    CreatePokeballEntries(pokeballViewModel)
                    db.getPokemonTrainers()
                    // Create and display the main view with associated ViewModels.
                    MainView(mainViewModel, pokemonViewModel, weatherViewModel, pokeballViewModel)
                }
            }
        }
    }

}


