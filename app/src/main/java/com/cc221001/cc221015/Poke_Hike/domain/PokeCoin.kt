package com.cc221001.cc221015.Poke_Hike.domain

data class PokeCoin(
    val id: Int? = 0,  // The unique identification number of the Pokemon.
    val name: String,  // The name of the Pokemon.
    val amount: Int = 0,  // Indicates whether the Pokemon is liked by the user (defaults to "false")
)