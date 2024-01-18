package com.cc221001.cc221015.Poke_Hike.stateModel

import com.cc221001.cc221015.Poke_Hike.domain.PokeCoin

data class PokeCoinViewState(
    val pokeCoins: List<PokeCoin?> = emptyList(),
    val pokeCoin: PokeCoin = PokeCoin(0, "",0),
    val openDialog: Boolean = false
)