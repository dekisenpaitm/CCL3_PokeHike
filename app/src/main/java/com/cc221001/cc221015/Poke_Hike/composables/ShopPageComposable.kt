package com.cc221001.cc221015.Poke_Hike.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.service.dto.CurrentWeather
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import okio.AsyncTimeout.Companion.condition
import java.util.Properties
import java.util.concurrent.locks.Condition
import java.util.logging.Filter

@Composable
fun GetWeatherResponse(weatherViewModel: WeatherViewModel): CurrentWeather? {
    val weather by weatherViewModel.weather.collectAsState(null)
    return weather
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun DisplayPokeballList(
    pokemonViewModel: PokemonViewModel,
    pokeballViewModel: PokeballViewModel,
    weatherViewModel: WeatherViewModel,
    pokeCoinViewModel: PokeCoinViewModel
) {
    // Collecting the list of Pokemons from the ViewModel.
    val weather = GetWeatherResponse(weatherViewModel = weatherViewModel)
    val condition = weather?.weather?.firstOrNull()?.main
    pokeballViewModel.getSpecialPokeball(condition.toString())
    val pokeballList = pokeballViewModel.pokemonViewState.collectAsState().value.pokeballs

        Column(modifier = Modifier
            .background(color = Color(0, 0, 0, 125))
            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
            .padding(20.dp,4.dp,20.dp)
            .fillMaxSize()) {

            if (weather == null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Retrieving the latest weather data...", color = Color.White)
                }
            } else {
            WeatherBox(
                weather = weather ,
                pokeballList = pokeballList,
                condition= condition)
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

@Composable
fun WeatherBox(weather:CurrentWeather, pokeballList:List<Pokeball?>, condition: String?) {
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
                    .matchParentSize()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            )
            Column(
                Modifier
                    .padding(top = 6.dp, bottom = 6.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                    text = "Weather currently: ${condition}!\n\n" +
                            "${pokeball.name} is now available.\n\n" +
                            "${pokeball.name}s contain: ${pokeball.type1}, ${pokeball.type2} & ${pokeball.type3} pokemons.",
                    color = Color.White,
                    textAlign = TextAlign.Center
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
    LazyColumn(
        modifier = Modifier
            .padding(top = 10.dp),
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
                    .padding(vertical = 4.dp)
            ) {
                PokeballsItem(
                    pokeCoinViewModel = pokeCoinViewModel,
                    pokemonViewModel = pokemonViewModel,
                    pokeball = pokeball)
                }
            }
        }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokeballsItem(
    pokeCoinViewModel: PokeCoinViewModel,
    pokemonViewModel: PokemonViewModel,
    pokeball: Pokeball?) {
    // Declare a state variable to track if the dialog is shown
    var showDialog by remember { mutableStateOf(false) }
    var pokemonBought by remember { mutableStateOf(false) }
    val currentCoins by pokeCoinViewModel.pokeCoinViewState.collectAsState()
    val currentAvailablePokemon by pokemonViewModel.pokemonViewState.collectAsState()
    // Spacer to add some space before the item starts.
    //println(currentAvailablePokemon.availableTypePokemon)
    Spacer(
        modifier = Modifier
            .height(6.dp)
            .fillMaxWidth()
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("x")
                    }
                    append(
                        when {
                            pokeball?.type1 != "All" -> "${currentAvailablePokemon.availableTypePokemon.size}"
                            else -> "${currentAvailablePokemon.availableAllPokemon.size}"
                        }
                    )
                },
                color = Color.White,
            )
        }

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
                            if (pokeball?.type1 != "All") {
                                pokemonViewModel.getAvailablePokemon(
                                    pokeball!!.type1,
                                    pokeball.type2,
                                    pokeball.type3
                                )
                                if (currentAvailablePokemon.availableTypePokemon.isEmpty()) {
                                    AsyncImage(
                                        model = pokeball.imageUrl,
                                        contentDescription = "Pokemon Image",
                                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                                            setToSaturation(
                                                0f
                                            )
                                        }),
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(MaterialTheme.shapes.small)
                                    )
                                } else {
                                    AsyncImage(
                                        model = pokeball.imageUrl,
                                        contentDescription = "Pokemon Image",
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(MaterialTheme.shapes.small)
                                    )
                                }
                            } else {
                                pokemonViewModel.getNotOwnedPokemon()
                                if (currentAvailablePokemon.availableAllPokemon.isEmpty()) {
                                    AsyncImage(
                                        model = pokeball.imageUrl,
                                        contentDescription = "Pokemon Image",
                                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                                            setToSaturation(
                                                0f
                                            )
                                        }),
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(MaterialTheme.shapes.small)
                                    )
                                } else {
                                    AsyncImage(
                                        model = pokeball.imageUrl,
                                        contentDescription = "Pokemon Image",
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(MaterialTheme.shapes.small)
                                    )
                                }
                            }

                        }
                        Box(
                            modifier = Modifier
                                .weight(1.8f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = pokeball.name.replaceFirstChar { it.titlecase() },
                                color = Color.White,
                                fontSize = 14.sp,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1.2f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                               text = "¢${pokeball.price}",
                                color = Color.White,
                                fontSize = 14.sp,
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
                if (pokeball?.type1 != "All") {
                    /*pokemonViewModel.getAvailablePokemon(
                        pokeball!!.type1,
                        pokeball.type2,
                        pokeball.type3
                    )*/
                    if (currentAvailablePokemon.availableTypePokemon.isEmpty()) {
                        CustomButtonGray(
                            text = "N/A", onClick = {
                            }, amount = 100,
                            amount2 = 80,
                            true
                        )
                    } else {
                        CustomButton(
                            text = "Buy", onClick = {
                                showDialog = true
                            }, amount = 100,
                            amount2 = 80,
                            true
                        )
                    }
                } else {
                    //pokemonViewModel.getNotOwnedPokemon()
                    if (currentAvailablePokemon.availableAllPokemon.isEmpty()) {
                        CustomButtonGray(
                            text = "N/A", onClick = {
                            }, amount = 100,
                            amount2 = 80,
                            true
                        )
                    } else {
                        CustomButton(
                            text = "Buy", onClick = {
                                showDialog = true
                            }, amount = 100,
                            amount2 = 80,
                            true
                        )
                    }
                }
            }
        }
        // Show the AlertDialog when showDialog is true
        if (showDialog && currentCoins.pokeCoin.amount >= pokeball!!.price) {
            AlertDialog(
                containerColor = Color(16, 0, 25, 200),
                onDismissRequest = {
                    // Dismiss the dialog when clicking outside of it
                    showDialog = false
                }, title = {
                    Text(text = "Buy ${pokeball?.name}?", color = Color.White)
                }, text = {
                    Text(
                        text = "Are you sure you want to buy ${pokeball?.name}?",
                        color = Color.White
                    )
                }, confirmButton = {
                    Surface(
                        color = Color(106, 84, 141, 255), // Set the background color of the surface
                        modifier = Modifier
                            .width(80.dp)
                            .height(50.dp)
                            .clickable(onClick = {
                                if (pokeball != null) {
                                    if (currentCoins.pokeCoin.amount >= pokeball.price) {
                                        pokeCoinViewModel.usePokeCoins(
                                            currentCoins.pokeCoin,
                                            pokeball.price
                                        )
                                        pokemonViewModel.getRandomPokemon(
                                            pokeball.type1,
                                            pokeball.type2,
                                            pokeball.type3
                                        )
                                        pokemonBought = true
                                        showDialog = false
                                    }
                                }
                            }) // Makes the surface clickable
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(4.dp), // Padding inside the Row
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White // Set icon color
                            )
                            Spacer(Modifier.width(4.dp)) // Spacer for the gap
                            Text(
                                text = "Yes",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }, dismissButton = {
                    // Customizing the confirm button
                    Surface(
                        color = Color(106, 84, 141, 255), // Set the background color of the surface
                        modifier = Modifier
                            .width(80.dp)
                            .height(50.dp)
                            .clickable(onClick = {
                                showDialog = false
                            }) // Makes the surface clickable
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(4.dp), // Padding inside the Row
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.White // Set icon color
                            )
                            Spacer(Modifier.width(4.dp)) // Spacer for the gap
                            Text(
                                text = "No",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                })
        } else if (showDialog && currentCoins.pokeCoin.amount < pokeball!!.price) {
            AlertDialog(
                containerColor = Color(16, 0, 25, 200),
                onDismissRequest = {
                    // Dismiss the dialog when clicking outside of it
                    showDialog = false
                }, title = {
                    Text(text = "Oops!", color = Color.White)
                }, text = {
                    Text(text = "Looks like you're out of coins!", color = Color.White)
                }, confirmButton = {
                    Surface(
                        color = Color(106, 84, 141, 255), // Set the background color of the surface
                        modifier = Modifier
                            .width(80.dp)
                            .height(50.dp)
                            .clickable(onClick = {
                                showDialog = false
                            }) // Makes the surface clickable
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(4.dp), // Padding inside the Row
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White // Set icon color
                            )
                            Spacer(Modifier.width(4.dp)) // Spacer for the gap
                            Text(
                                text = "Yes",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                })
        }
        if (pokemonBought) {
            if (pokeball != null) {
                BuyPokemon(
                    pokeball = pokeball,
                    pokemonViewModel = pokemonViewModel,
                    pokemonBought = pokemonBought,
                    onClose = { pokemonBought = false })
            }
        }
    }
}
@Composable
fun BuyPokemon(pokeball:Pokeball, pokemonViewModel: PokemonViewModel, pokemonBought: Boolean, onClose: () -> Unit){
        if (pokeball.name != "") {
            DisplayPokemonMessage(pokemonViewModel,pokemonBought, onClose)
        }
}

@Composable
fun DisplayPokemonMessage(
    pokemonViewModel: PokemonViewModel, pokemonBought: Boolean,  onClose: () -> Unit
) {
    val randomPokemon by pokemonViewModel.pokemonViewState.collectAsState()

    if (randomPokemon.pokemon?.name != "" && pokemonBought) {
        AlertDialog(
            containerColor = Color(16, 0, 25, 200),
            onDismissRequest = onClose,
            title = {
                Text(
                    text = "Congratulations!",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth() // Take up the full width
                        .padding(16.dp)  // Add padding for spacing
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Display the Pokemon image
                    item {
                        AsyncImage(
                            model = randomPokemon.pokemon?.imageUrl,
                            contentDescription = "Random Pokemon Image",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .padding(20.dp)
                        )
                    }
                    item {
                        // Display a message to the user
                        Text(
                            text = "You got ${randomPokemon.pokemon?.name?.replaceFirstChar { it.uppercaseChar() }}!",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            },
            confirmButton = {
                // Customizing the confirm button
                Surface(
                    color = Color(106, 84, 141, 255), // Set the background color of the surface
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp)
                        .clickable(onClick = onClose) // Makes the surface clickable
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))// Optional if you want extra clipping, but shape already does this
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "OK",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier
                                .padding(
                                    vertical = 8.dp,
                                    horizontal = 16.dp
                                ) // Add padding for better touch area and aesthetics
                                .fillMaxWidth() // Optional, for making the text take up the full width of the surface
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth() // Make sure the AlertDialog itself fills the width
                .wrapContentHeight(Alignment.CenterVertically) // Vertically center the AlertDialog
        )
    }
}
