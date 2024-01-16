package com.cc221001.cc221015.Poke_Hike.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221001.cc221015.Poke_Hike.composables.DisplayTrainerProfile
import com.cc221001.cc221015.Poke_Hike.composables.DisplayWeather
import com.cc221001.cc221015.Poke_Hike.composables.ErrorScreen
import com.cc221001.cc221015.Poke_Hike.composables.MyPokemonList
import com.cc221001.cc221015.Poke_Hike.composables.SetBackgroundMain
import com.cc221001.cc221015.Poke_Hike.composables.landingPage
import com.cc221001.cc221015.Poke_Hike.composables.mainScreen
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel

// https://kotlinlang.org/docs/sealed-classes.html
// Define a sealed class named 'Screen' to represent different screens in the app.
// Sealed classes are used for representing restricted class hierarchies, where a value can have one of the types from a limited set.
sealed class Screen(val route: String) {
    // Object declarations for different screens, each with a unique route string.
    // These objects extend the Screen class and provide specific routes for navigation.
    // The use of objects here ensures that only a single instance of each screen type exists.

    object Home : Screen("home")   // Represents the first screen with route "first"
    object Weather : Screen("weather") // Represents the second screen with route "second"
    object Favourites : Screen("favourites")   // Represents the third screen with route "third"
    object List : Screen("list") // Represents the fourth screen with route "fourth"
    object Profile : Screen("Profile") // Represents the fourth screen with route "fourth"
    object Shop : Screen("Shop") // Represents the fourth screen with route "fourth"

}

// Usage: This sealed class is particularly useful in a Jetpack Compose navigation setup,
// where you need to define routes for different composables in a type-safe manner.
// Each screen is represented as a singleton object, making it easy to reference them throughout the app.

// Opt-in for the experimental Material3 API which is still in development.
@OptIn(ExperimentalMaterial3Api::class)
// MainView is a Composable function that creates the main view of your app.
@Composable
fun MainView(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel, weatherViewModel: WeatherViewModel) {


    // Collect the current state of the main view from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()

    // Create or remember a NavController for navigation between screens.
    val navController = rememberNavController()

    // Scaffold is a material design container that includes standard layout structures.
    Scaffold(
        // Define the bottom navigation bar for the Scaffold.
        bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen) }
    ) {
        // NavHost manages composable destinations for navigation.
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it), // Apply padding from the Scaffold.
            startDestination = Screen.Home.route // Define the starting screen.
        ) {
            // Define the composable function for the 'Home' route.
            composable(Screen.Home.route) {
                SetBackgroundMain() // Set the main background for this view.
                mainViewModel.getPokemonTrainer() // Fetch the Pokemon trainer information.
                // Check if the pokemon trainers list is not empty.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Home)
                    mainScreen(mainViewModel) // Show the main screen if trainers exist.
                } else {
                    mainViewModel.selectScreen(Screen.Home)
                    landingPage(mainViewModel,pokemonViewModel) // Show the landing page otherwise.
                }
            }

            // Define the composable function for the 'Weather' route.
            composable(Screen.Weather.route) {
                mainViewModel.getPokemonTrainer()
                SetBackgroundMain()
                // Similar logic as above for the fourth screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Weather)
                    DisplayWeather(weatherViewModel)
                } else {
                    mainViewModel.selectScreen(Screen.Weather)
                    ErrorScreen()
                }
            }

            // Define the composable function for the 'Favourites' route.
            composable(Screen.Favourites.route) {
                SetBackgroundMain()
                mainViewModel.getPokemonTrainer()
                // Similar logic as the 'First' route but for the second screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Favourites)
                    pokemonViewModel.getFavPokemon()
                    MyPokemonList(pokemonViewModel, true)
                } else {
                    mainViewModel.selectScreen(Screen.Favourites)
                    ErrorScreen()
                }
            }

            // Define the composable function for the 'List' route.
            composable(Screen.List.route) {
                SetBackgroundMain()
                mainViewModel.getPokemonTrainer()
                // Similar logic as above for the third screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.List)
                    pokemonViewModel.getPokemon()
                    MyPokemonList(pokemonViewModel, false)
                } else {
                    mainViewModel.selectScreen(Screen.List)
                    ErrorScreen()
                }
            }

            // Define the composable function for the 'Fourth' route.
            composable(Screen.Profile.route) {
                mainViewModel.getPokemonTrainer()
                SetBackgroundMain()
                // Similar logic as above for the fourth screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Profile)
                    DisplayTrainerProfile(mainViewModel, pokemonViewModel)
                } else {
                    mainViewModel.selectScreen(Screen.Profile)
                    ErrorScreen()
                }
            }

            // Define the composable function for the 'Fourth' route.
            composable(Screen.Shop.route) {
                mainViewModel.getPokemonTrainer()
                SetBackgroundMain()
                // Similar logic as above for the fourth screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Shop)
                    //DISPLAY SHOP PAGE HERE
                } else {
                    mainViewModel.selectScreen(Screen.Shop)
                    ErrorScreen()
                }
            }
        }
    }
}



// Define a Composable function for creating a Bottom Navigation Bar.
@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen) {
    val context = LocalContext.current
    // BottomNavigation is a Material Design component that provides bottom navigation.
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary // Set the background color of the navigation bar.
    ) {
        // NavigationBarItem for the 'Home' screen.
        NavigationBarItem(
            selected = (selectedScreen == Screen.Home), // Determine if this item is selected based on the current screen.
            onClick = { navController.navigate(Screen.Home.route) }, // Define action on click, navigating to the 'Home' route.
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "") } // Set the icon for this item.
        )

        // NavigationBarItem for the 'Weather' screen.
        NavigationBarItem(
            // Similar configuration as above for the 'Weather' screen.
            selected = (selectedScreen == Screen.Weather),
            onClick = {
                navController.navigate(Screen.Weather.route) },
            icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "") }
        )

        // NavigationBarItem for the 'Favourites' screen.
        NavigationBarItem(
            // Similar configuration as the first item but for the 'Favourites' screen.
            selected = (selectedScreen == Screen.Favourites),
            onClick = { navController.navigate(Screen.Favourites.route) },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "") }
        )

        // NavigationBarItem for the 'List' screen.
        NavigationBarItem(
            // Similar configuration as above for the 'Third' screen.
            selected = (selectedScreen == Screen.List),
            onClick = { navController.navigate(Screen.List.route) },
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "") }
        )

        //REMOVE THE PROFILE ICON FROM HERE AND CREATE A TOP NAVIGATION FOR THE PROFILE
        //CHANGE THE CODE BELOW TO SHOP!

        // NavigationBarItem for the 'Profile' screen.
        NavigationBarItem(
            // Similar configuration as above for the 'Fourth' screen.
            selected = (selectedScreen == Screen.Profile),
            onClick = { navController.navigate(Screen.Profile.route) },
            icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "") }
        )
    }
}









