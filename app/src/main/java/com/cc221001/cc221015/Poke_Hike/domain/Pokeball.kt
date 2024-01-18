package com.cc221001.cc221015.Poke_Hike.domain

data class Pokeball(
    val number: Int? = 0,  // The unique identification number of the Pokemon.
    val name: String,  // The name of the Pokemon.
    val type0: String,  // Weather Required for Pokeball
    val type1: String, // Pokemon Type that can be in this Pokeball
    val type2: String, // Pokemon Type that can be in this Pokeball
    val type3: String, // Pokemon Type that can be in this Pokeball
    val price: Int = 0,  // Indicates whether the Pokemon is liked by the user (defaults to "false")
    val imageUrl: String  // The URL of the Pokemon's image. Defaults to a standard Pokemon sprite URL.
)