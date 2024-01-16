package com.cc221001.cc221015.Poke_Hike.domain

data class Pokeball (
    val number: Int,  // The unique identification number of the Pokemon.
    val name: String,  // The name of the Pokemon.
    val type0: String,  // The primary type of the Pokemon (e.g., Water, Fire).
    val price: Int = 0,  // Indicates whether the Pokemon is liked by the user (defaults to "false")
    val imageUrl: String = "https://img.pokemondb.net/sprites/diamond-pearl/normal/$name.png",
    // The URL of the Pokemon's image. Defaults to a standard Pokemon sprite URL.
)