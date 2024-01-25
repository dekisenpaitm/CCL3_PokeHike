package com.cc221001.cc221015.Poke_Hike.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.views.Screen
import java.util.Locale


// Composable function to display a list of Pokemon.
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun MyPokemonList(pokemonViewModel: PokemonViewModel, listType: String) {
    // Collecting the list of Pokemons from the ViewModel.
    val pokemonList = pokemonViewModel.pokemonViewState.collectAsState().value.pokemons
    var currentListEntry by remember { mutableIntStateOf(0) }
    // A Row to display the list of Pokemon.
    val searchText by pokemonViewModel.searchText.collectAsState()
    val isSearching by pokemonViewModel.isSearching.collectAsState()
    val pokemonListBar by pokemonViewModel.pokemonList.collectAsState()

    val listState= rememberLazyListState()

    Column(horizontalAlignment = Alignment.CenterHorizontally,  modifier = Modifier
        .background(color = Color(0, 0, 0, 125))
        .padding(20.dp, 20.dp, 20.dp, 0.dp)
        .fillMaxSize()) {

        if(listType=="all") {
            SearchBar(
                placeholder = { Text(text = "Search Pokemon", color = Color.White) },
                query = searchText,//text showed on SearchBar
                onQueryChange = pokemonViewModel::onSearchTextChange, //update the value of searchText
                onSearch = pokemonViewModel::onSearchTextChange, //the callback to be invoked when the input service triggers the ImeAction.Search action
                active = isSearching, //whether the user is searching or not
                onActiveChange = { pokemonViewModel.onToogleSearch() },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color(
                        255,
                        255,
                        255,
                        50
                    )
                ),//the callback to be invoked when this search bar's active state is changed
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 10.dp)
                ) {
                    CustomSplitter(h = 2)
                }
                LazyColumn(state = listState) {
                    println("this is your pokemonlist: $pokemonList")
                    items(pokemonListBar) { pokemon ->
                        val listNumber = if (pokemon != null) {
                            if (pokemon?.number!! < 1) pokemon?.number else if (pokemon?.number > 1) pokemon?.number?.minus(1) else 0
                        } else {
                            0
                        }
                        Box(modifier = Modifier.clickable {
                            pokemonViewModel.onToogleSearch()
                            if (listNumber != null) {
                                currentListEntry = listNumber
                            }
                        }.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp))
                        {
                            pokemon?.name?.let { Text(text = it, color=Color.White) }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 0.dp, vertical = 10.dp)
                        ) {
                            CustomSplitter(h = 2)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                CustomSplitter(h = 2)
            }
        }
        // Calling PokemonList Composable to display the actual list.
        PokemonList(pokemonList = pokemonList, pokemonViewModel, listType, currentListEntry)
    }
}

// This function decides whether to display the user's favorite Pokemon or the entire Pokedex based on the 'favorite' flag.
// It uses a Column for vertical arrangement and dynamically sets the title text.
@Composable
fun ChoiceButton(pokemonViewModel: PokemonViewModel) {
    val currentListType by remember { pokemonViewModel.currentListType }.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // First Button (Collection)
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
                    .background(
                        if (currentListType == PokemonViewModel.ListType.FAVORITES) Color(
                            106,
                            84,
                            141,
                            255
                        )
                        else Color(58, 42, 75, 255)
                    )
                    .clickable { pokemonViewModel.getFavPokemon() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Favourites",
                    modifier = Modifier.padding(8.dp),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            // Second Button (Owned)
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
                    .background(
                        if (currentListType == PokemonViewModel.ListType.OWNED) Color(
                            106, 84, 141, 255
                        )
                        else Color(58, 42, 75, 255)
                    )
                    .clickable { pokemonViewModel.getOwnedPokemon() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Owned",
                    modifier = Modifier.padding(8.dp),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}


// Composable function to display a list of Pokemon.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    pokemonList: List<Pokemon?>,
    pokemonViewModel: PokemonViewModel,
    listType: String,
    currentListEntry: Int
) {

    val listState = rememberLazyListState()

    Column(
    ) {
        if (listType == "favourite" || listType == "owned") {
            ChoiceButton(pokemonViewModel = pokemonViewModel)
        }
        LazyColumn(state = listState) {
            // Iterating over each Pokemon in the pokemonList.
            items(pokemonList) { pokemon ->
                // PokemonItem Composable is called for each Pokemon in the list.
                // It displays individual Pokemon details.
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    PokemonItem(pokemon = pokemon, pokemonViewModel = pokemonViewModel, myList = true)
                }
            }
        }
    }

    LaunchedEffect(key1 = currentListEntry){
        listState.scrollToItem(index=currentListEntry)
    }

}

// This function creates a scrollable list of Pokemon, leveraging LazyColumn for performance.
// Each item in the list is represented by the PokemonItem Composable.

// Opt-in for Experimental Layout API and define the Composable function for displaying individual Pokemon items.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonItem(pokemon: Pokemon?, pokemonViewModel: PokemonViewModel, myList: Boolean) {

    val currentListType by remember { pokemonViewModel.currentListType }.collectAsState()
    // Spacer to add some space before the item starts.
    Spacer(
        modifier = Modifier
            .height(5.dp)
            .fillMaxWidth()
    )

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
        Box(
            modifier = Modifier.weight(1.1f), contentAlignment = Alignment.Center
        ) {
            if (pokemon != null) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = "Pokemon Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }

        // Box for displaying the Pokemon's name.
        Box(
            modifier = itemModifier
                .fillMaxHeight()
                .weight(1.6f),
            contentAlignment = Alignment.Center
        ) {
            if (pokemon != null) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.titlecase() },
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Box for displaying the Pokemon's types.
        Box(
            modifier = itemModifier
                .fillMaxHeight()
                .weight(1.2f),
            contentAlignment = Alignment.Center
        ) {
            // List of specific Pokemon types.
            val specificTypes = listOf(
                "fire",
                "water",
                "electric",
                "grass",
                "bug",
                "normal",
                "poison",
                "flying",
                "ground",
                "ghost",
                "psychic",
                "fairy",
                "fighting",
                "rock",
                "dragon",
                "ice"
            )
            if (pokemon != null) {
                val matchingTypes = listOf(pokemon.type0, pokemon.type1).mapNotNull { type ->
                    specificTypes.find { specificType ->
                        type.toString().contains(specificType, ignoreCase = true)
                    }?.toLowerCase(
                        Locale.ROOT
                    )
                }
                if (matchingTypes.isNotEmpty()) {
                    Column {
                        matchingTypes.forEach { pokemonType ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = pokemonType.replaceFirstChar { it.uppercase() },
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // Box for the like/unlike button.
        Box(
            modifier = itemModifier
                .fillMaxHeight()
                .weight(0.4f),
            contentAlignment = Alignment.Center
        ) {
            if (pokemon != null) {
                IconButton(onClick = {
                    when {
                        pokemon.liked == "true" && currentListType == PokemonViewModel.ListType.FAVORITES ->
                            pokemonViewModel.unlikePokemon(pokemon, "favorite")
                        pokemon.liked == "true" && currentListType == PokemonViewModel.ListType.OWNED ->
                            pokemonViewModel.unlikePokemon(pokemon, "owned")
                        pokemon.liked == "true" && currentListType == PokemonViewModel.ListType.ALL ->
                            pokemonViewModel.unlikePokemon(pokemon, "all")
                        pokemon.liked != "true" && currentListType == PokemonViewModel.ListType.FAVORITES ->
                            pokemonViewModel.likePokemon(pokemon, "favorite")
                        pokemon.liked != "true" && currentListType == PokemonViewModel.ListType.OWNED ->
                            pokemonViewModel.likePokemon(pokemon, "owned")
                        pokemon.liked != "true" && currentListType == PokemonViewModel.ListType.ALL ->
                            pokemonViewModel.likePokemon(pokemon, "all")
                    }
                }) {
                    val tint = if (pokemon.liked == "true") Color(200, 84, 141, 255) else Color.Gray
                    Icon(
                        Icons.Default.Favorite, "Like", tint = tint, modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Box for the owned/notOwned
        Box(
            modifier = itemModifier
                .fillMaxHeight()
                .weight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            if (pokemon != null) {
                val tint = if (pokemon.owned == "true") Color.White else Color.Gray
                Icon(
                    Icons.Default.CheckCircle, "Like", tint = tint, modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// This function creates a UI for each Pokemon item. It includes an image, name, types, and a like button.
// The layout is done using FlowRow for a responsive design. The like button interacts with the PokemonViewModel for state changes.