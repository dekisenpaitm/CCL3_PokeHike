package com.cc221001.cc221015.Poke_Hike.viewModel

import androidx.lifecycle.ViewModel
import com.cc221001.cc221015.Poke_Hike.data.PokeCoinBaseHandler
import com.cc221001.cc221015.Poke_Hike.domain.PokeCoin
import com.cc221001.cc221015.Poke_Hike.stateModel.PokeCoinViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokeCoinViewModel(private val db: PokeCoinBaseHandler):ViewModel() {
    private val _pokeCoinViewState = MutableStateFlow(PokeCoinViewState())
    val pokeCoinViewState: StateFlow<PokeCoinViewState> = _pokeCoinViewState.asStateFlow()

    fun getPokeCoins(){
        _pokeCoinViewState.update{it.copy(pokeCoins=db.getAllPokeCoins())}
    }

    fun updatePokeCoins(pokeCoin: PokeCoin){
        _pokeCoinViewState.update{it.copy(pokeCoins=db.updatePokeCoin(pokeCoin = pokeCoin))}
        getPokeCoins()
    }

    fun deletePokeCoinStash(pokeCoin:PokeCoin){
        db.deletePokeCoin(pokeCoin)
        getPokeCoins()
    }

    fun createPokeCoinStash(name:String, amount:Int){
        val pokeCoin = PokeCoin(name = name, amount = amount)
        db.insertCoin(pokeCoin)
    }

    fun dismissDialog() {
        _pokeCoinViewState.update { it.copy(openDialog = false) }
    }

}