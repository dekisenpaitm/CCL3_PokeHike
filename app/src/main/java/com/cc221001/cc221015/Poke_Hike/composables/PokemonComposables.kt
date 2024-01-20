package com.cc221001.cc221015.Poke_Hike.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import java.util.Locale


// Composable function to display a list of Pokemon.
@SuppressLint("SuspiciousIndentation")
@Composable
fun MyPokemonList(pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    // Collecting the list of Pokemons from the ViewModel.
    val pokemonList = pokemonViewModel.pokemonViewState.collectAsState().value.pokemons
        // A Row to display the list of Pokemon.
        Row (modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd= 20.dp, bottomEnd = 0.dp, bottomStart=0.dp))){
            // Calling PokemonList Composable to display the actual list.
            PokemonList(pokemonList = pokemonList, pokemonViewModel, favorite)
        }
    }

// This function decides whether to display the user's favorite Pokemon or the entire Pokedex based on the 'favorite' flag.
// It uses a Column for vertical arrangement and dynamically sets the title text.
@Composable
fun ChoiceButton(pokemonViewModel: PokemonViewModel){
    Box(
        contentAlignment = Alignment.Center, // Center aligns the content in the Box
        modifier = Modifier
            .fillMaxWidth() // Makes the Box fill the entire screen
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Add spacing between the buttons
            verticalAlignment = Alignment.CenterVertically // Aligns items vertically to the center
        ) {
            // First Button
            CustomButton(
                text = "Favourites",
                onClick = { pokemonViewModel.getFavPokemon() },
                amount = 120,
                amount2 = 50
            )

            // Second Button
            CustomButton(
                text = "Owned",
                onClick = { pokemonViewModel.getOwnedPokemon() },
                amount = 120,
                amount2 = 50
            )
        }
    }
}
// Composable function to display a list of Pokemon.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(pokemonList: List<Pokemon?>, pokemonViewModel: PokemonViewModel, favorite: Boolean) {

    // LazyColumn is used for efficiently displaying a list that can be scrolled.
    // It only renders the items that are currently visible on screen.

Column(modifier= Modifier
    .background(color = Color(0, 0, 0, 125))
    .padding(top = 20.dp)
    .fillMaxSize()){
    if(favorite) {
        ChoiceButton(pokemonViewModel = pokemonViewModel)
    }
    LazyColumn ()
    {
        // Iterating over each Pokemon in the pokemonList.
        items(pokemonList) { pokemon ->
            // PokemonItem Composable is called for each Pokemon in the list.
            // It displays individual Pokemon details.
            Box(modifier= Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))) {
                PokemonItem(pokemon = pokemon, pokemonViewModel = pokemonViewModel, favorite)
            }
        }
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
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(255, 255, 255, 50))
            .border(2.dp, color = Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        maxItemsInEachRow = 5 // Sets the max number of items in each row.
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
                        .size(90.dp)
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
                    color=Color.White,
                )
            }
        }

        // Box for displaying the Pokemon's types.
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f), contentAlignment = Alignment.Center) {
            // List of specific Pokemon types.
            val specificTypes = listOf("fire", "water", "electric", "grass", "bug", "normal", "poison", "flying", "ground", "ghost", "psychic", "fairy", "fighting", "rock", "dragon", "ice")
            if (pokemon != null) {
                val matchingTypes = listOf(pokemon.type0, pokemon.type1).mapNotNull { type ->
                    specificTypes.find { specificType -> type.toString().contains(specificType, ignoreCase = true) }?.toLowerCase(
                        Locale.ROOT)
                }
                if (matchingTypes.isNotEmpty()) {
                    Column {
                        matchingTypes.forEach { pokemonType ->
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Text(text = pokemonType.replaceFirstChar { it.uppercase() }, color=Color.White)
                            }
                        }
                    }
                }
            }
        }

        // Box for the like/unlike button.
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(0.5f),
            contentAlignment = Alignment.Center) {
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

        // Box for the owned/notOwned
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(0.5f),
            contentAlignment = Alignment.Center) {
            if (pokemon != null) {
                val tint = if (pokemon.owned == "true") Color.Green else Color.Gray
                Icon(Icons.Default.CheckCircle, "Like", tint = tint, modifier = Modifier.size(20.dp))
            }
        }
    }
}

// This function creates a UI for each Pokemon item. It includes an image, name, types, and a like button.
// The layout is done using FlowRow for a responsive design. The like button interacts with the PokemonViewModel for state changes.