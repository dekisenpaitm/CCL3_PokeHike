package com.cc221001.cc221015.Poke_Hike.stateModel

import com.cc221001.cc221015.Poke_Hike.domain.PokemonTrainer
import com.cc221001.cc221015.Poke_Hike.views.Screen

// Represents the state of the main screen in the application.
data class MainViewState(
    val pokemonTrainers: List<PokemonTrainer> = emptyList(),  // List of Pokemon trainers in the view.
    val editPokemonTrainer: PokemonTrainer = PokemonTrainer(0,"", "",""),  // Pokemon trainer being edited.
    val selectedScreen: Screen = Screen.Home,  // The selected screen/tab in the UI.
    val openDialog: Boolean = false  // Indicates whether a dialog is open in the UI.
)
