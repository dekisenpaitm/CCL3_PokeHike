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
import com.cc221001.cc221015.Poke_Hike.composables.CreateTrainerStash
import com.cc221001.cc221015.Poke_Hike.data.PokeCoinBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.PokeballBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.PokemonBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.StepCounterBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.TrainerBaseHandler
import com.cc221001.cc221015.Poke_Hike.domain.StepCounter
import com.cc221001.cc221015.Poke_Hike.service.StepCounterService
import com.cc221001.cc221015.Poke_Hike.ui.theme.MyApplicationTheme
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.StepCounterViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import com.cc221001.cc221015.Poke_Hike.views.MainView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Database handler for Pokemon trainers && ViewModel for the main screen.
    private val db = TrainerBaseHandler(this)
    private val mainViewModel = MainViewModel(db)

    // Database handler for Pokemon entities && ViewModel for the Pokemon-related view.
    private val pdb = PokemonBaseHandler(this)
    private val pokemonViewModel = PokemonViewModel(pdb)

    // Database handler for Pokeball entities && ViewModel for the Pokeball-related view.
    private val pbdb = PokeballBaseHandler(this)
    private val pokeballViewModel = PokeballViewModel(pbdb)

    //Database handler for PokeCoin entities && ViewModel for the PokeCoin-related view
    private val pcdb = PokeCoinBaseHandler(this)
    private val pokeCoinViewModel = PokeCoinViewModel(pcdb)

    private val scdb = StepCounterBaseHandler(this)
    private val stepCounterViewModel = StepCounterViewModel(scdb, pcdb)

    // ViewModel for the Weather-related view.
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
            println("StepCounter Started")
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
                    CreateTrainerStash(pokeCoinViewModel)
                    //.oupokeCoinViewModel.observeCoinStashChanges()
                    //pokeCoinViewModel.updateCoinRepository()
                    //pokeCoinViewModel.usePokeCoins(pcdb.getPokeCoinById(0),100)
                    scdb.insertSteps(StepCounter(0,0))
                    db.getPokemonTrainers()
                    // Create and display the main view with associated ViewModels.
                    MainView(mainViewModel, pokemonViewModel, weatherViewModel, pokeballViewModel, stepCounterViewModel, pokeCoinViewModel)
                }
            }
        }
    }

}


