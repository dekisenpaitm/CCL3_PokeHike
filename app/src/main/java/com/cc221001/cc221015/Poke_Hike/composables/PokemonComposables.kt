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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import java.util.Locale

// Composable function to display a list of Pokemon.
@Composable
fun MyPokemonList(pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    // Collecting the list of Pokemons from the ViewModel.
    val pokemonList = pokemonViewModel.pokemonViewState.collectAsState().value.pokemons

    // Using a Column to layout elements vertically.
    Column {
        // A Row for displaying the title, with dynamic text based on the 'favorite' flag.
        Row(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            val text = if (favorite) "My Favorites" else "Pokedex"
            Text(text = text, fontSize = 40.sp, color = Color.White)
        }

        // A Row to display the list of Pokemon.
        Row {
            // Calling PokemonList Composable to display the actual list.
            PokemonList(pokemonList = pokemonList, pokemonViewModel, favorite)
        }
    }
}

// This function decides whether to display the user's favorite Pokemon or the entire Pokedex based on the 'favorite' flag.
// It uses a Column for vertical arrangement and dynamically sets the title text.


// Composable function to display a list of Pokemon.
@Composable
fun PokemonList(pokemonList: List<Pokemon?>, pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    // LazyColumn is used for efficiently displaying a list that can be scrolled.
    // It only renders the items that are currently visible on screen.
    LazyColumn {
        // Iterating over each Pokemon in the pokemonList.
        items(pokemonList) { pokemon ->
            // PokemonItem Composable is called for each Pokemon in the list.
            // It displays individual Pokemon details.
            PokemonItem(pokemon = pokemon, pokemonViewModel = pokemonViewModel, favorite)
        }
    }
}

// This function creates a scrollable list of Pokemon, leveraging LazyColumn for performance.
// Each item in the list is represented by the PokemonItem Composable.

// Opt-in for Experimental Layout API and define the Composable function for displaying individual Pokemon items.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonItem(pokemon: Pokemon?, pokemonViewModel: PokemonViewModel, favorite: Boolean) {
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
            if (pokemon != null) {
                AsyncImage(
                    model = pokemon.imageUrl,
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
            if (pokemon != null) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.titlecase() },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }

        // Box for displaying the Pokemon's types.
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f), contentAlignment = Alignment.Center) {
            // List of specific Pokemon types.
            val specificTypes = listOf("fire", "water", "electric", "grass", "bug", "normal", "poison", "ground", "ghost", "psychic", "fairy", "fighting", "rock", "dragon", "ice")
            if (pokemon != null) {
                val matchingTypes = listOf(pokemon.type0, pokemon.type1).mapNotNull { type ->
                    specificTypes.find { specificType -> type.toString().contains(specificType, ignoreCase = true) }?.toLowerCase(
                        Locale.ROOT)
                }
                if (matchingTypes.isNotEmpty()) {
                    Column {
                        matchingTypes.forEach { pokemonType ->
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Text(text = pokemonType.replaceFirstChar { it.uppercase() })
                            }
                        }
                    }
                }
            }
        }

        // Box for the like/unlike button.
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f), contentAlignment = Alignment.Center) {
            if (pokemon != null) {
                IconButton(onClick = {
                    if (pokemon.liked == "true") {
                        pokemonViewModel.unlikePokemon(pokemon, favorite)
                    } else {
                        pokemonViewModel.likePokemon(pokemon, favorite)
                    }
                }) {
                    val tint = if (pokemon.liked == "true") Color.Red else Color.Gray
                    Icon(Icons.Default.Favorite, "Like", tint = tint, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

// This function creates a UI for each Pokemon item. It includes an image, name, types, and a like button.
// The layout is done using FlowRow for a responsive design. The like button interacts with the PokemonViewModel for state changes.