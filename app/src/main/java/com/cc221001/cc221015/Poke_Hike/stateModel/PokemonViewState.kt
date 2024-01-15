package com.cc221001.cc221015.Poke_Hike.stateModel

import com.cc221001.cc221015.Poke_Hike.domain.Pokemon
import com.cc221001.cc221015.Poke_Hike.Screen

// Represents the state of the Pokemon-related view in the application.
data class PokemonViewState(
    val pokemons: List<Pokemon?> = emptyList(),  // List of Pokemon entities in the view.
    val editPokemon: Pokemon = Pokemon(0,"", "","","",""),  // Pokemon entity being edited.
    val selectedScreen: Screen = Screen.Third,  // The selected screen/tab in the UI.
    val openDialog: Boolean = false  // Indicates whether a dialog is open in the UI.
)
