package com.cc221001.cc221015.Poke_Hike.views

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.composables.CreateOnBoarding
import com.cc221001.cc221015.Poke_Hike.composables.DisplayLandingPage
import com.cc221001.cc221015.Poke_Hike.composables.DisplayLoadingPage
import com.cc221001.cc221015.Poke_Hike.composables.DisplayPokeballList
import com.cc221001.cc221015.Poke_Hike.composables.DisplayPopUp
import com.cc221001.cc221015.Poke_Hike.composables.DisplayTrainerProfile
import com.cc221001.cc221015.Poke_Hike.composables.DisplayWeather
import com.cc221001.cc221015.Poke_Hike.composables.ErrorScreen
import com.cc221001.cc221015.Poke_Hike.composables.MyPokemonList
import com.cc221001.cc221015.Poke_Hike.composables.WeatherComposable
import com.cc221001.cc221015.Poke_Hike.composables.landingPage
import com.cc221001.cc221015.Poke_Hike.composables.mainScreen
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.OnBoardingViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.StepCounterViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel


// https://kotlinlang.org/docs/sealed-classes.html
// Define a sealed class named 'Screen' to represent different screens in the app.
// Sealed classes are used for representing restricted class hierarchies, where a value can have one of the types from a limited set.
sealed class Screen(val route: String) {
    // Object declarations for different screens, each with a unique route string.
    // These objects extend the Screen class and provide specific routes for navigation.
    // The use of objects here ensures that only a single instance of each screen type exists.

    object Home : Screen("Home")   // Represents the first screen with route "first"
    object Weather : Screen("Weather") // Represents the second screen with route "second"
    object Collection : Screen("Collection")   // Represents the third screen with route "third"
    object List : Screen("Pokedex") // Represents the fourth screen with route "fourth"
    object Profile : Screen("Profile") // Represents the fourth screen with route "fourth"
    object Shop : Screen("Shop") // Represents the fourth screen with route "fourth"

}

// Usage: This sealed class is particularly useful in a Jetpack Compose navigation setup,
// where you need to define routes for different composables in a type-safe manner.
// Each screen is represented as a singleton object, making it easy to reference them throughout the app.

// Opt-in for the experimental Material3 API which is still in development.
@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
// MainView is a Composable function that creates the main view of your app.
@Composable
fun MainView(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel, weatherViewModel: WeatherViewModel, pokeballViewModel: PokeballViewModel, stepCounterViewModel: StepCounterViewModel, pokeCoinViewModel: PokeCoinViewModel,onBoardingViewModel: OnBoardingViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val weather by weatherViewModel.weather.collectAsState(null)
    val navController = rememberNavController()
    var nextStep by remember { mutableStateOf(false) }
    var loadingScreen by remember { mutableStateOf(true) }
    val onBoardingState = onBoardingViewModel.onboardingViewState.collectAsState()
    var isConnected by remember { mutableStateOf(false) }
    var context = LocalContext.current

    mainViewModel.getPokemonTrainer() // Fetch the Pokemon trainer information.
    pokeCoinViewModel.startObeserving()
    if (loadingScreen) {
        android.os.Handler()
            .postDelayed({ loadingScreen = false}, 3000)
        WeatherComposable(weather = weather)
        DisplayLoadingPage()
    }
    else{
        isConnected = isInternetAvailable(context = context)
        if (state.value.pokemonTrainers.isEmpty() && !nextStep) {
            CreateOnBoarding(onBoardingViewModel)
            onBoardingViewModel.resetAllStates()
            WeatherComposable(weather = weather)
            DisplayLandingPage(onClick = {
                nextStep = isConnected
            })
            if(!isConnected) {
               NoInternetPopUp(
                   title = "Are you connected?" ,
                   text = "It seems like you're currently not connected to the internet. Please make sure you have an internet connection available upon using this app!",
                   onAcceptClick= { isConnected = isInternetAvailable(context = context)})
            }
        } else if (state.value.pokemonTrainers.isEmpty() && nextStep) {
            WeatherComposable(weather = weather)
            landingPage(
                mainViewModel = mainViewModel,
                pokemonViewModel = pokemonViewModel,
                pokeballViewModel = pokeballViewModel,
                stepCounterViewModel = stepCounterViewModel,
                pokeCoinViewModel = pokeCoinViewModel,
                context = context,
            )
        } else if (state.value.pokemonTrainers.isNotEmpty()) {
            nextStep = false
            Scaffold(
                // Define the bottom navigation bar for the Scaffold.
                topBar = { MyTopAppBar(navController, state.value.selectedScreen) },
                bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen) },
                containerColor = Color.White,
            ) {
                WeatherComposable(weather = weather)
                // NavHost manages composable destinations for navigation.
                NavHost(
                    navController = navController,
                    modifier = Modifier.padding(it), // Apply padding from the Scaffold.
                    startDestination = Screen.Home.route // Define the starting screen.
                ) {
                    // Define the composable function for the 'Home' route.
                    composable(Screen.Home.route) {
                        if (state.value.pokemonTrainers.isNotEmpty()) {
                            onBoardingViewModel.getState("homePage")
                            pokeCoinViewModel.getPokeCoins()
                            mainViewModel.selectScreen(Screen.Home)
                            mainScreen(
                                mainViewModel,
                                pokeCoinViewModel,
                                navController
                            )
                            if(onBoardingState.value.currentState?.value == false){
                                DisplayPopUp(
                                    onBoardingViewModel= onBoardingViewModel,
                                    title = "Home" ,
                                    text ="On the Home Page, you can view your currently collected coins, quickly access the shop through a button, and read our reminders about how PokeHike works.", pageName="homePage")
                            }
                        } else {
                            mainViewModel.selectScreen(Screen.Home)
                            ErrorScreen()
                        }
                    }
                    // Define the composable function for the 'Weather' route.
                    composable(Screen.Weather.route) {
                        if (state.value.pokemonTrainers.isNotEmpty()) {
                            onBoardingViewModel.getState("weatherPage")
                            mainViewModel.selectScreen(Screen.Weather)
                            DisplayWeather(weatherViewModel)
                            if(onBoardingState.value.currentState?.value == false){
                                DisplayPopUp(onBoardingViewModel= onBoardingViewModel, title = "Weather" , text ="The Weather Page displays the Current Weather based on the location and provides a Forecast for the next 5 days. Please ensure that you are connected to the internet!", pageName="weatherPage")
                            }
                        } else {
                            mainViewModel.selectScreen(Screen.Weather)
                            ErrorScreen()
                        }
                    }

                    // Define the composable function for the 'Collection' route.
                    composable(Screen.Collection.route) {
                        if (state.value.pokemonTrainers.isNotEmpty()) {
                            onBoardingViewModel.getState("favPage")
                            mainViewModel.selectScreen(Screen.Collection)
                            pokemonViewModel.getOwnedPokemon()
                            MyPokemonList(pokemonViewModel, "owned")
                            if(onBoardingState.value.currentState?.value == false){
                                DisplayPopUp(onBoardingViewModel= onBoardingViewModel, title = "Collection" , text ="The Collection page shows you your Pokemon. Favorites are the Pokemon you liked by clicking the little heart-shaped button. Owned refers to the Pokemon you obtained from the Pokeballs you bought.", pageName="favPage")
                            }
                        } else {
                            mainViewModel.selectScreen(Screen.Collection)
                            ErrorScreen()
                        }
                    }

                    // Define the composable function for the 'List' route.
                    composable(Screen.List.route) {
                        if (state.value.pokemonTrainers.isNotEmpty()) {
                            onBoardingViewModel.getState("listPage")
                            mainViewModel.selectScreen(Screen.List)
                            pokemonViewModel.getPokemon()
                            MyPokemonList(pokemonViewModel, "all")
                            if(onBoardingState.value.currentState?.value == false){
                                DisplayPopUp(onBoardingViewModel= onBoardingViewModel, title = "Pokedex" , text ="The Pokedex displays all the Pokemon currently available in the app. By clicking the heart-shaped button, you can add the Pokemon to your collection of favorite Pokemon.", pageName="listPage")
                            }
                        } else {
                            mainViewModel.selectScreen(Screen.List)
                            ErrorScreen()
                        }
                    }
                    composable(Screen.Profile.route) {
                        if (state.value.pokemonTrainers.isNotEmpty()) {
                            onBoardingViewModel.getState("profilePage")
                            mainViewModel.selectScreen(Screen.Profile)
                            DisplayTrainerProfile(
                                mainViewModel,
                                pokemonViewModel,
                                pokeCoinViewModel
                            )
                            if(onBoardingState.value.currentState?.value == false){
                                DisplayPopUp(onBoardingViewModel= onBoardingViewModel, title = "Trainer Page" , text ="On the Trainer Page, you can check and update your credentials. It is also possible to delete your account, but please be cautious as this action will remove everything, including your collected Pokemon and Coins!", pageName="profilePage")
                            }
                        } else {
                            mainViewModel.selectScreen(Screen.Profile)
                            ErrorScreen()
                        }
                    }
                    composable(Screen.Shop.route) {
                        if (state.value.pokemonTrainers.isNotEmpty()) {
                            onBoardingViewModel.getState("shopPage")
                            mainViewModel.selectScreen(Screen.Shop)
                            DisplayPokeballList(
                                pokemonViewModel,
                                pokeballViewModel,
                                weatherViewModel,
                                pokeCoinViewModel
                            )
                            if(onBoardingState.value.currentState?.value == false){
                                DisplayPopUp(onBoardingViewModel= onBoardingViewModel, title = "Shop" , text ="On the Shop page, you can spend your collected Pokecoins: either for a weather-based Pokeball that contains only specific types or the standard Pokeball that contains all available Pokemon. Numbers above items in the Shop indicate how many Pokemon are still available to get from that type of Pokeball.", pageName="shopPage")
                            }
                        } else {
                            mainViewModel.selectScreen(Screen.Shop)
                            ErrorScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoInternetPopUp(title:String, text:String, onAcceptClick:()->Unit){
    val customFontFamily = FontFamily(Font(R.font.aldrich))
    AlertDialog(containerColor = Color(0, 0, 0, 200),
        modifier= Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                2.dp, Color(255, 255, 255, 75),
                RoundedCornerShape(20.dp)
            ),
        onDismissRequest = {},
        title = {
            Text(
                text = title,
                color = Color.White,
                fontFamily = customFontFamily
            )
        }, text = {
            Text(
                text = text,
                color = Color.White,
                fontFamily = customFontFamily
            )
        }, confirmButton = {
            Surface(
                color = Color(106, 84, 141, 255), // Set the background color of the surface
                modifier = Modifier
                    .width(80.dp)
                    .height(50.dp)
                    .clickable(onClick = onAcceptClick)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "RETRY", color=Color.White, fontFamily = customFontFamily)
                }
            }
        }
    )
}



@Composable
fun MyTopAppBar(navController: NavHostController, selectedScreen: Screen) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp),
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
                                fontSize = 30.sp,
                                maxLines = 1,
                                text = selectedScreen.route,
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
                        .padding(0.dp, 8.dp, 0.dp, 0.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        onClick = { navController.navigate(Screen.Profile.route) },
                        enabled = true,
                    ) {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "", modifier= Modifier
                            .size(40.dp)
                            .padding(bottom = 6.dp)
                            .clip(RoundedCornerShape(10.dp)), tint = Color.White)
                    }
                }
            }
        }
    }
}

fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}


// Define a Composable function for creating a Bottom Navigation Bar.
@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen) {
    val iconSize = 24.dp // Adjust the size as needed

    BottomNavigation(
        elevation = 0.dp,
        backgroundColor = Color(0, 0, 0, 125),
    ) {
        NavigationBarItem(
            selected = (selectedScreen == Screen.Home),
            onClick = { navController.navigate(Screen.Home.route) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon",
                    tint = Color.White,
                    modifier = Modifier.size(iconSize)
                )
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                indicatorColor = Color(106, 84, 141, 255)
            )
        )

        NavigationBarItem(
            selected = (selectedScreen == Screen.Weather),
            onClick = { navController.navigate(Screen.Weather.route) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.sunicon),
                    contentDescription = "Weather Icon",
                    tint = Color.White,
                    modifier = Modifier.size(iconSize)
                )
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                indicatorColor = Color(106, 84, 141, 255)
            )
        )

        NavigationBarItem(
            selected = (selectedScreen == Screen.Collection),
            onClick = { navController.navigate(Screen.Collection.route) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.pokeballicon),
                    contentDescription = "Pokeball Icon",
                    tint = Color.White,
                    modifier = Modifier.size(iconSize)
                )
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                indicatorColor = Color(106, 84, 141, 255)
            )
        )

        NavigationBarItem(
            selected = (selectedScreen == Screen.List),
            onClick = { navController.navigate(Screen.List.route) },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Pokedex Icon",
                    tint = Color.White,
                    modifier = Modifier.size(iconSize)
                )
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                indicatorColor = Color(106, 84, 141, 255)
            )
        )

        NavigationBarItem(
            selected = (selectedScreen == Screen.Shop),
            onClick = { navController.navigate(Screen.Shop.route) },
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shop Icon",
                    tint = Color.White,
                    modifier = Modifier.size(iconSize)
                )
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                indicatorColor = Color(106, 84, 141, 255)
            )
        )
    }
}









