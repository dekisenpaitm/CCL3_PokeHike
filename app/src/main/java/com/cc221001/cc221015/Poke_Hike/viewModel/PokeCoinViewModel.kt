package com.cc221001.cc221015.Poke_Hike.viewModel

import androidx.lifecycle.ViewModel
import com.cc221001.cc221015.Poke_Hike.data.PokeHikeDatabaseHandler
import com.cc221001.cc221015.Poke_Hike.domain.PokeCoin
import com.cc221001.cc221015.Poke_Hike.service.StepCounterRepository
import com.cc221001.cc221015.Poke_Hike.stateModel.PokeCoinViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokeCoinViewModel(private val db: PokeHikeDatabaseHandler):ViewModel() {
    private val _pokeCoinViewState = MutableStateFlow(PokeCoinViewState())
    val pokeCoinViewState: StateFlow<PokeCoinViewState> = _pokeCoinViewState.asStateFlow()


    fun startObeserving(){
        StepCounterRepository.stepCountLiveData.observeForever {
            getPokeCoins()
        }
    }


    fun getPokeCoins():PokeCoin{
        _pokeCoinViewState.update{it.copy(pokeCoin=db.getPokeCoinById(1))}
        return db.getPokeCoinById(1)
    }

    fun usePokeCoins(pokeCoin: PokeCoin, amount: Int){
        _pokeCoinViewState.update{it.copy(pokeCoins=db.usePokeCoin(pokeCoin = pokeCoin, amount))}
        getPokeCoins()
    }

    fun deletePokeCoinStash(pokeCoin:PokeCoin){
        db.deletePokeCoin(pokeCoin)
        //getPokeCoins()
    }

    fun createPokeCoinStash(id:Int, name:String, amount:Int){
        val pokeCoin = PokeCoin(name = name, amount = amount)
        db.insertCoin(pokeCoin)
    }

    fun dismissDialog() {
        _pokeCoinViewState.update { it.copy(openDialog = false) }
    }

}