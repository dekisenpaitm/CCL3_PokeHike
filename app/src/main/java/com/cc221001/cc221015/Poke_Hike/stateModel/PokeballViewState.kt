package com.cc221001.cc221015.Poke_Hike.stateModel

import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.views.Screen

data class PokeballViewState(
    val pokeballs: List<Pokeball?> = emptyList(),  // List of Pokemon entities in the view.
    val editPokemon: Pokeball = Pokeball(0,"", "",0,""),  // Pokemon entity being edited.
    val selectedScreen: Screen = Screen.Shop,  // The selected screen/tab in the UI.
    val openDialog: Boolean = false  // Indicates whether a dialog is open in the UI.
)
