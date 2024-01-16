package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.service.dto.WeatherResponse
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.WeatherViewModel
import okio.AsyncTimeout.Companion.condition

@Composable
fun GetWeatherResponse(weatherViewModel: WeatherViewModel): WeatherResponse? {
    val weather by weatherViewModel.weather.collectAsState(null)

    return weather
}

@Composable
fun DisplayPokeballList(pokeballViewModel: PokeballViewModel, weatherViewModel: WeatherViewModel) {
    // Collecting the list of Pokemons from the ViewModel.
    val weather = GetWeatherResponse(weatherViewModel = weatherViewModel)
    val condition = weather?.weather?.first()?.main

    println(condition)

    pokeballViewModel.getSpecialPokeball(condition.toString())

    val pokeballList = pokeballViewModel.pokemonViewState.collectAsState().value.pokeballs



    // Using a Column to layout elements vertically.
    Column {
        // A Row for displaying the title, with dynamic text based on the 'favorite' flag.
        Row(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            val text = "Shop"
            Text(text = text, fontSize = 40.sp, color = Color.White)
        }

        // A Row to display the list of Pokemon.
        Row {
            // Calling PokemonList Composable to display the actual list.
            PokeballList(pokeballs = pokeballList, pokeballViewModel = pokeballViewModel)
        }
    }
}

@Composable
fun PokeballList(pokeballs: List<Pokeball?>, pokeballViewModel: PokeballViewModel) {
    // LazyColumn is used for efficiently displaying a list that can be scrolled.
    // It only renders the items that are currently visible on screen.
    LazyColumn {
        // Iterating over each Pokemon in the pokemonList.
        items(pokeballs) { pokeball ->
            // PokemonItem Composable is called for each Pokemon in the list.
            // It displays individual Pokemon details.
            PokeballsItem(pokeball = pokeball)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokeballsItem(pokeball: Pokeball?){
    // Spacer to add some space before the item starts.
    Spacer(modifier = Modifier
        .height(5.dp)
        .fillMaxWidth())

    // FlowRow is used to arrange items in a horizontal flow that wraps.
    FlowRow(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(255, 255, 255, 125))
            .border(color = Color.Black, width = 1.dp),
        maxItemsInEachRow = 4 // Sets the max number of items in each row.
    ) {
        // Modifier for individual items in the FlowRow.
        val itemModifier = Modifier.clip(RoundedCornerShape(8.dp))

        // Box for displaying the Pokemon image.
        Box(contentAlignment = Alignment.Center) {
            if (pokeball != null) {
                AsyncImage(
                    model = pokeball.imageUrl,
                    contentDescription = "Pokemon Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }

        // Box for displaying the Pokemon's name.
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f), contentAlignment = Alignment.Center) {
            if (pokeball != null) {
                Text(
                    text = pokeball.name.replaceFirstChar { it.titlecase() },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }

        // Box for displaying the Pokemon's types.
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f), contentAlignment = Alignment.Center) {
            if (pokeball != null) {
                Text(
                    text = pokeball.type0.replaceFirstChar { it.titlecase() },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }

        // Box for displaying the Pokemon's types.
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f), contentAlignment = Alignment.Center) {
            if (pokeball != null) {
                Text(
                    text = pokeball.price.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
    }
}