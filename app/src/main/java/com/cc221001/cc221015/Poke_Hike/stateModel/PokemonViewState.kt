package com.cc221001.cc221015.Poke_Hike.stateModel

import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.views.Screen

// Represents the state of the Pokemon-related view in the application.
data class PokemonViewState(
    val pokemons: List<Pokemon?> = emptyList(),  // List of Pokemon entities in the view.
    val pokemon: Pokemon? = Pokemon(0,"", "","","","", ""),
    val editPokemon: Pokemon = Pokemon(0,"", "","","","", ""),
    val selectedScreen: Screen = Screen.List,  // The selected screen/tab in the UI.
    val openDialog: Boolean = false  // Indicates whether a dialog is open in the UI.
)
