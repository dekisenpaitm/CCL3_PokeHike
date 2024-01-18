package com.cc221001.cc221015.Poke_Hike.viewModel

import androidx.lifecycle.ViewModel
import com.cc221001.cc221015.Poke_Hike.data.PokeCoinBaseHandler
import com.cc221001.cc221015.Poke_Hike.domain.PokeCoin
import com.cc221001.cc221015.Poke_Hike.service.CoinStashRepository
import com.cc221001.cc221015.Poke_Hike.service.StepCounterRepository
import com.cc221001.cc221015.Poke_Hike.stateModel.PokeCoinViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PokeCoinViewModel(private val db: PokeCoinBaseHandler):ViewModel() {
    private val _pokeCoinViewState = MutableStateFlow(PokeCoinViewState())
    val pokeCoinViewState: StateFlow<PokeCoinViewState> = _pokeCoinViewState.asStateFlow()

    fun observeCoinStashChanges(){
        CoinStashRepository.coinStashLiveData.observeForever{ newCoinValue ->
            println("This is the new coin value: $newCoinValue")
            collectPokeCoins(newCoinValue)
        }
    }

    fun updateCoinRepository(){
        val pokeCoin = db.getPokeCoinById(0)
        StepCounterRepository.updateStepCount(pokeCoin.amount)
    }

    fun collectPokeCoins(newCoinValue:Int){
        db.updatePokeCoin(db.getPokeCoinById(0),newCoinValue)
        getPokeCoins()
        //usePokeCoins(db.getPokeCoinById(0), 10)
        println("this is your current stash: ${db.getPokeCoinById(0)}")
    }

    fun getPokeCoins():PokeCoin{
        _pokeCoinViewState.update{it.copy(pokeCoin=db.getPokeCoinById(0))}
        return db.getPokeCoinById(0)
    }

    fun usePokeCoins(pokeCoin: PokeCoin, amount: Int){
        _pokeCoinViewState.update{it.copy(pokeCoins=db.usePokeCoin(pokeCoin = pokeCoin, amount))}
        CoinStashRepository.minusCoinStash(amount)
        getPokeCoins()
    }

    fun addPokeCoins(pokeCoin: PokeCoin, amount: Int){
        _pokeCoinViewState.update{it.copy(pokeCoins=db.addPokeCoin(pokeCoin = pokeCoin, amount))}
        CoinStashRepository.plusCoinStash(amount)
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