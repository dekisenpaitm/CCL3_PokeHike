package com.cc221001.cc221015.Poke_Hike.api

import com.cc221001.cc221015.Poke_Hike.data.PokemonApiResult
import com.cc221001.cc221015.Poke_Hike.data.PokemonsApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// This interface defines the API endpoints and HTTP methods for interacting with the PokeAPI.
interface PokemonService {

    // HTTP GET request to fetch a list of Pokemons with optional query parameter 'limit'.
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int): Call<PokemonsApiResult>

    // HTTP GET request to fetch information about a specific Pokemon by its 'number' in the path.
    @GET("pokemon/{number}")
    fun getPokemon(@Path("number") number: Int): Call<PokemonApiResult>
}
