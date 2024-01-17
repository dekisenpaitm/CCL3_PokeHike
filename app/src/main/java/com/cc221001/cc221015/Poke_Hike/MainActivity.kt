package com.cc221001.cc221015.Poke_Hike

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import com.cc221001.cc221015.Poke_Hike.composables.CreatePokeballEntries
import com.cc221001.cc221015.Poke_Hike.data.PokeballBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.PokemonBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.TrainerBaseHandler
import com.cc221001.cc221015.Poke_Hike.ui.theme.MyApplicationTheme
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import com.cc221001.cc221015.Poke_Hike.views.MainView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val providerPackageName = "com.google.android.apps.healthdata"

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

    // Create a set of permissions for health connect
    val permissions =
        setOf(
            HealthPermission.getReadPermission(StepsRecord::class),
            HealthPermission.getWritePermission(StepsRecord::class)
        )

    // Create the permissions launcher
    val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()

    val requestPermissions = registerForActivityResult(requestPermissionActivityContract) { granted ->
        if (granted.containsAll(permissions)) {
            // Permissions successfully granted
            // You can proceed with any necessary actions here
        } else {
            // Lack of required permissions
            // We can displaying a message to the user or smth
        }
    }

    // Check permissions and launch the request if necessary
    @RequiresApi(34)
    suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (granted.containsAll(permissions)) {
            // Permissions already granted; proceed with inserting or reading data
            // You can perform your desired operations here
        } else {
            // Request permissions
            requestPermissions.launch(permissions)
        }
    }


    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        checkHealthConnectSDK()
        GlobalScope.launch {
            checkPermissionsAndRun(HealthConnectClient.getOrCreate(applicationContext))
        }
    }

    private fun checkHealthConnectSDK() {
        val availabilityStatus = HealthConnectClient.getSdkStatus(applicationContext, providerPackageName)
        if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE) {
            return // early return as there is no viable integration
        }
        if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED) {
            // Optionally redirect to package installer to find a provider
            redirectToPackageInstaller()
            return
        }

        // Continue with other initialization or operations
        val healthConnectClient = HealthConnectClient.getOrCreate(applicationContext)
        // Issue operations with healthConnectClient
    }

    private fun redirectToPackageInstaller() {
        val uriString = "market://details?id=$providerPackageName&url=healthconnect%3A%2F%2Fonboarding"
        applicationContext.startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                setPackage("com.android.vending")
                data = Uri.parse(uriString)
                putExtra("overlay", true)
                putExtra("callerId", applicationContext.packageName)
            }
        )
    }
}





