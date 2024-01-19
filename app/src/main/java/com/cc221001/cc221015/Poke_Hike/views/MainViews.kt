package com.cc221001.cc221015.Poke_Hike.views

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.composables.CoinCounterDisplay
import com.cc221001.cc221015.Poke_Hike.composables.DisplayPokeballList
import com.cc221001.cc221015.Poke_Hike.composables.DisplayTrainerProfile
import com.cc221001.cc221015.Poke_Hike.composables.DisplayWeather
import com.cc221001.cc221015.Poke_Hike.composables.ErrorScreen
import com.cc221001.cc221015.Poke_Hike.composables.MyPokemonList
import com.cc221001.cc221015.Poke_Hike.composables.landingPage
import com.cc221001.cc221015.Poke_Hike.composables.mainScreen
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.StepCounterViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import kotlin.math.round

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
    object Profile : Screen("profile") // Represents the fourth screen with route "fourth"
    object Shop : Screen("shop") // Represents the fourth screen with route "fourth"

}

// Usage: This sealed class is particularly useful in a Jetpack Compose navigation setup,
// where you need to define routes for different composables in a type-safe manner.
// Each screen is represented as a singleton object, making it easy to reference them throughout the app.

// Opt-in for the experimental Material3 API which is still in development.
@OptIn(ExperimentalMaterial3Api::class)
// MainView is a Composable function that creates the main view of your app.
@Composable
fun MainView(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel, weatherViewModel: WeatherViewModel, pokeballViewModel: PokeballViewModel, stepCounterViewModel: StepCounterViewModel, pokeCoinViewModel: PokeCoinViewModel) {

    // Collect the current state of the main view from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()

    // Create or remember a NavController for navigation between screens.
    val navController = rememberNavController()

    // Scaffold is a material design container that includes standard layout structures.
    Scaffold(
        // Define the bottom navigation bar for the Scaffold.
        topBar = { MyTopAppBar(navController, state.value.selectedScreen)},
        bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen) },
        containerColor = Color.White,
    ) {
        Image(
            painter = painterResource(id = R.drawable.hills_background),
            contentDescription = "Login_Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        // NavHost manages composable destinations for navigation.
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it), // Apply padding from the Scaffold.
            startDestination = Screen.Home.route // Define the starting screen.
        ) {
            // Define the composable function for the 'Home' route.
            composable(Screen.Home.route) {
                mainViewModel.getPokemonTrainer() // Fetch the Pokemon trainer information.
                // Check if the pokemon trainers list is not empty.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    CoinCounterDisplay(pokeCoinViewModel)
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

            // Define the composable function for the 'Profile' route.
            composable(Screen.Profile.route) {
                mainViewModel.getPokemonTrainer()
                // Similar logic as above for the Profile screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Profile)
                    DisplayTrainerProfile(mainViewModel, pokemonViewModel)
                } else {
                    mainViewModel.selectScreen(Screen.Profile)
                    ErrorScreen()
                }
            }

            // Define the composable function for the 'Shop' route.
            composable(Screen.Shop.route) {
                mainViewModel.getPokemonTrainer()
                // Similar logic as above for the fourth screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Shop)
                    DisplayPokeballList(pokemonViewModel,pokeballViewModel,weatherViewModel,pokeCoinViewModel)
                } else {
                    mainViewModel.selectScreen(Screen.Shop)
                    ErrorScreen()
                }
            }
        }
    }
}

@Composable
fun MyTopAppBar(navController: NavHostController, selectedScreen: Screen) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth()
            .height(150.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.height(32.dp)) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                text = "Pokehike",
                                color=Color.White
                            )
                        }
                    }
                }
            }
            // TopAppBar Content
            Row() {
                // Navigation Icon - Box1
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.bodyLarge) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                fontSize = 50.sp,
                                maxLines = 1,
                                text = "Home",
                                color=Color.White
                            )
                        }
                    }
                }
                // Title - Box2
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                text = ""
                            )
                        }
                    }
                }

                // Account Icon - Box3
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(0.dp,8.dp,0.dp,0.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        onClick = { navController.navigate(Screen.Profile.route) },
                        enabled = true,
                    ) {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "", modifier=Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)), tint = Color.White)
                    }
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
        backgroundColor = Color(0,0,0,125),
    ) {
        // NavigationBarItem for the 'Home' screen.
        NavigationBarItem(
            selected = (selectedScreen == Screen.Home), // Determine if this item is selected based on the current screen.
            onClick = { navController.navigate(Screen.Home.route) }, // Define action on click, navigating to the 'Home' route.
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "", tint=Color.White) }, // Set the icon for this item.
            colors = androidx.compose.material3.NavigationBarItemDefaults
                .colors(
                    indicatorColor = Color(80,80,125)))

        // NavigationBarItem for the 'Weather' screen.
        NavigationBarItem(
            // Similar configuration as above for the 'Weather' screen.
            selected = (selectedScreen == Screen.Weather),
            onClick = {
                navController.navigate(Screen.Weather.route) },
            icon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = "", tint=Color.White) }, // Set the icon for this item.
            colors = androidx.compose.material3.NavigationBarItemDefaults
                .colors(
                    indicatorColor = Color(80,80,125)))

        // NavigationBarItem for the 'Favourites' screen.
        NavigationBarItem(
            // Similar configuration as the first item but for the 'Favourites' screen.
            selected = (selectedScreen == Screen.Favourites),
            onClick = { navController.navigate(Screen.Favourites.route) },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "", tint=Color.White) }, // Set the icon for this item.
            colors = androidx.compose.material3.NavigationBarItemDefaults
                .colors(
                    indicatorColor = Color(80,80,125)))
        // NavigationBarItem for the 'List' screen.
        NavigationBarItem(
            // Similar configuration as above for the 'Third' screen.
            selected = (selectedScreen == Screen.List),
            onClick = { navController.navigate(Screen.List.route) },
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "", tint=Color.White) }, // Set the icon for this item.
            colors = androidx.compose.material3.NavigationBarItemDefaults
                .colors(
                    indicatorColor = Color(80,80,125)))

        NavigationBarItem(
            // Similar configuration as above for the 'Fourth' screen.
            selected = (selectedScreen == Screen.Shop),
            onClick = { navController.navigate(Screen.Shop.route) },
            icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "", tint=Color.White) }, // Set the icon for this item.
            colors = androidx.compose.material3.NavigationBarItemDefaults
                .colors(
                    indicatorColor = Color(80,80,125)))
    }
}









