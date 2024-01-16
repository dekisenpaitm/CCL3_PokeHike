package com.cc221001.cc221015.Poke_Hike.viewModel

import androidx.lifecycle.ViewModel
import com.cc221001.cc221015.Poke_Hike.data.PokeballBaseHandler
import com.cc221001.cc221015.Poke_Hike.domain.Pokeball
import com.cc221001.cc221015.Poke_Hike.stateModel.PokeballViewState
import com.cc221001.cc221015.Poke_Hike.views.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokeballViewModel(private val db: PokeballBaseHandler) : ViewModel() {
    private val _pokeballViewState = MutableStateFlow(PokeballViewState())
    val pokemonViewState: StateFlow<PokeballViewState> = _pokeballViewState.asStateFlow()

    // Fetch and load all Pokemon from the database.
    fun getPokeballs() {
        _pokeballViewState.update { it.copy(pokeballs = db.getAllPokeballs()) }
    }

    // Fetch and load favorite Pokemon from the database.
    fun getSpecialPokeball(weather: String) {
        _pokeballViewState.update { it.copy(pokeballs = db.getSpecialPokeball(weather)) }
    }

    // Select a screen in the UI.
    fun selectScreen(screen: Screen) {
        _pokeballViewState.update { it.copy(selectedScreen = screen) }
    }

    fun createPokeball(id: Int, name: String, type: String, price: Int, imageUrl: String) {
        val pokeball = Pokeball(id, name, type, price, imageUrl)
        db.insertPokemonBall(pokeball)
    }
}

