package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.service.dto.CurrentWeather
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import okio.AsyncTimeout.Companion.condition

@Composable
fun GetWeatherResponse(weatherViewModel: WeatherViewModel): CurrentWeather? {
    val weather by weatherViewModel.weather.collectAsState(null)

    return weather
}

@Composable
fun DisplayPokeballList(
    pokemonViewModel: PokemonViewModel,
    pokeballViewModel: PokeballViewModel,
    weatherViewModel: WeatherViewModel,
    pokeCoinViewModel: PokeCoinViewModel
) {
    // Collecting the list of Pokemons from the ViewModel.
    val weather = GetWeatherResponse(weatherViewModel = weatherViewModel)

    // Check if weather data is still loading


        Column(modifier = Modifier
            .background(color = Color(0, 0, 0, 125))
            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
            .padding(20.dp)) {

            if (weather == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Retrieving the latest weather data...", color = Color.White)
                }
            } else {
                val condition = weather.weather.firstOrNull()?.main
                pokeballViewModel.getSpecialPokeball(condition.toString())
                val pokeballList = pokeballViewModel.pokemonViewState.collectAsState().value.pokeballs

            val specialPokeball = pokeballList.firstOrNull()
            specialPokeball?.let { pokeball ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(vertical = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = weather.smallbackground()),
                        contentDescription = "Background",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                    Column(
                        Modifier
                            .padding(top = 6.dp, bottom = 6.dp)
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Now is ${condition}!\n" + "You can get a ${pokeball.name}.\n" +
                                    "${pokeball.name} has pokemons of ${pokeball.type1}, ${pokeball.type2} and ${pokeball.type3} types.\n" +
                                    "Normal PokeBall has pokemons of all types.",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            CustomSplitter(h = 2)
            // A Row to display the list of Pokemon.
            Row() {
                // Calling PokeballList Composable to display the actual list.
                PokeballList(
                    pokemonViewModel = pokemonViewModel,
                    pokeballs = pokeballList,
                    pokeballViewModel = pokeballViewModel,
                    pokeCoinViewModel = pokeCoinViewModel
                )
            }
        }
    }
}

@Composable
fun PokeballList(
    pokemonViewModel: PokemonViewModel,
    pokeballs: List<Pokeball?>,
    pokeballViewModel: PokeballViewModel,
    pokeCoinViewModel: PokeCoinViewModel
) {
    // State to track whether a Pokemon has been bought
    var pokemonBought by remember { mutableStateOf(false) }
    // LazyColumn is used for efficiently displaying a list that can be scrolled.
    // It only renders the items that are currently visible on screen.
    LazyColumn(
        modifier = Modifier
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Iterating over each Pokemon in the pokemonList.
        items(pokeballs) { pokeball ->
            // PokemonItem Composable is called for each Pokemon in the list.
            // It displays individual Pokemon details.
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxSize()
            ) {
                PokeballsItem(pokeCoinViewModel = pokeCoinViewModel,
                    pokemonViewModel = pokemonViewModel,
                    pokeball = pokeball,
                    onBuyClick = {
                        println("You bought ${pokeball?.name}!")
                        if (pokeball != null) {
                            pokemonViewModel.getRandomPokemon(
                                pokeball.type1, pokeball.type2, pokeball.type3
                            )
                            pokemonBought = true
                        }
                    })
                }
            }
        }
    // Display the Pokemon message conditionally outside LazyColumn
    if (pokemonBought) {
        val randomPokemon by pokemonViewModel.pokemonViewState.collectAsState()
        if (randomPokemon != null) {
            DisplayPokemonMessage(pokemonViewModel) {
                // Reset the state when the message is dismissed
                pokemonBought = false
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokeballsItem(
    pokeCoinViewModel: PokeCoinViewModel,
    pokemonViewModel: PokemonViewModel,
    pokeball: Pokeball?,
    onBuyClick: (Pokeball) -> Unit
) {
    // Declare a state variable to track if the dialog is shown
    var showDialog by remember { mutableStateOf(false) }
    val currentCoins by pokeCoinViewModel.pokeCoinViewState.collectAsState()
    //val pokemonViewModel: PokemonViewModel = viewModel()

    // Spacer to add some space before the item starts.
    Spacer(
        modifier = Modifier
            .height(5.dp)
            .fillMaxWidth()
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        // Box for Image, Text, and Price
        Box(
            modifier = Modifier
                .weight(3f)
                .height(80.dp)
                .background(Color(255, 255, 255, 50), RoundedCornerShape(10.dp))
                .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                if (pokeball != null) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = pokeball.imageUrl,
                            contentDescription = "Pokemon Image",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = pokeball.name.replaceFirstChar { it.titlecase() },
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$ ${pokeball.price},-",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .width(8.dp)
                .weight(0.1f)
        )

        // Button
        Box(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            CustomButton(
                text = "Buy", onClick = {
                    showDialog = true
                }, amount = 100,
                amount2 = 80,
                true
            )
        }
    }


    // Show the AlertDialog when showDialog is true
    if (showDialog) {
        AlertDialog(onDismissRequest = {
            // Dismiss the dialog when clicking outside of it
            showDialog = false
        }, title = {
            Text(text = "Buy ${pokeball?.name}?")
        }, text = {
            Text(text = "Are you sure you want to buy ${pokeball?.name}?")
        }, confirmButton = {
            Button(onClick = {
                // Call the onBuyClick callback when confirming the purchase
                if (pokeball != null) {
                    if (currentCoins.pokeCoin.amount >= pokeball.price) {
                        pokeCoinViewModel.usePokeCoins(currentCoins.pokeCoin, pokeball.price)
                        onBuyClick(pokeball)
                        showDialog = false
                    }
                }

            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Text(text = "Yes", fontWeight = FontWeight.Bold)
            }
        }, dismissButton = {
            Button(onClick = {
                // Dismiss the dialog when canceling the purchase
                showDialog = false
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                Text(text = "No", color = Color.White)
            }
        })
    }

}

@Composable
fun DisplayPokemonMessage(
    pokemonViewModel: PokemonViewModel, onDismiss: () -> Unit = {}
) {
    val randomPokemon by pokemonViewModel.pokemonViewState.collectAsState()

    if (randomPokemon != null) {
        AlertDialog(onDismissRequest = onDismiss, title = {
            // Display the Pokemon image.
            AsyncImage(
                model = randomPokemon.pokemon?.imageUrl,
                contentDescription = "Random Pokemon Image",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }, text = {
            // Display a message to the user.
            Text(
                text = "Congratulations!\nYou have got a ${randomPokemon.pokemon?.name?.replaceFirstChar { it.uppercaseChar() }}!",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }, confirmButton = {
            // You can customize the confirm button if needed.
            Button(onClick = onDismiss) {
                Text(text = "OK")
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
        )
    }
}