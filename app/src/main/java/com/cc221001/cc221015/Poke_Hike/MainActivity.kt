package com.cc221001.cc221015.Poke_Hike

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.cc221001.cc221015.Poke_Hike.data.OnBoardingStateHandler
import com.cc221001.cc221015.Poke_Hike.data.PokeCoinBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.PokeballBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.PokemonBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.StepCounterBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.TrainerBaseHandler
import com.cc221001.cc221015.Poke_Hike.handler.PermissionHandler
import com.cc221001.cc221015.Poke_Hike.service.StepCounterService
import com.cc221001.cc221015.Poke_Hike.ui.theme.MyApplicationTheme
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.OnBoardingViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.StepCounterViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import com.cc221001.cc221015.Poke_Hike.views.MainView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //David Shenanigans
    private val permissionHandler = PermissionHandler(this)
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACTIVITY_RECOGNITION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.POST_NOTIFICATIONS
    )

    private val requiredPermissionsLowerVersion = arrayOf(
        Manifest.permission.ACTIVITY_RECOGNITION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.FOREGROUND_SERVICE
    )




    // Database handler for Pokemon trainers && ViewModel for the main screen.
    private val db = TrainerBaseHandler(this)
    private val mainViewModel = MainViewModel(db)

    private val obdb = OnBoardingStateHandler(this)
    private val onBoardingViewModel = OnBoardingViewModel(obdb)

    // UI logic based on connectivity status
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

    //Service to check if there's internet available

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

        if (permissionHandler.hasPermissions(requiredPermissions)) {
            startStepTrackingService()
        } else {
            permissionHandler.requestPermissions(requiredPermissions)
        }

        setContent {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(mainViewModel, pokemonViewModel, weatherViewModel, pokeballViewModel, stepCounterViewModel, pokeCoinViewModel, onBoardingViewModel)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            println("PERMISSION given")
            startStepTrackingService()
        } else {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU) {
                if (permissionHandler.hasPermissions(requiredPermissionsLowerVersion)) {
                    startStepTrackingService()
                    println("PERMISSION given")
                }else{
                    println("PERMISSION not given")
                }
            }else{
                println("PERMISSION not given")
            }
        }
    }

    private fun startStepTrackingService() {
        val intent = Intent(this, StepCounterService::class.java).apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        }
        startService(intent)
    }


}


