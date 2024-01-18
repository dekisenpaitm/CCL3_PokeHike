package com.cc221001.cc221015.Poke_Hike.composables

import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel

fun CreatePokeballEntries(pokeballViewModel: PokeballViewModel){
    pokeballViewModel.createPokeball( "Safariball", "Clear", 1000, "https://archives.bulbagarden.net/media/upload/3/3d/Bag_Nest_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "Heavyball", "Clouds", 1000, "https://archives.bulbagarden.net/media/upload/4/46/Bag_Heavy_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "Dreamball", "Thunderstorm", 1000, "https://archives.bulbagarden.net/media/upload/6/6a/Bag_Dream_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "Premierball", "Snow", 1000, "https://archives.bulbagarden.net/media/upload/d/da/Bag_Premier_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "Moonball", "Fog", 1000, "https://archives.bulbagarden.net/media/upload/2/26/Bag_Moon_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "Fastball", "Drizzle", 1000, "https://archives.bulbagarden.net/media/upload/3/3c/Bag_Fast_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "Netball", "Rain", 1000, "https://archives.bulbagarden.net/media/upload/3/3b/Bag_Net_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "Mistball", "Mist", 1000, "https://archives.bulbagarden.net/media/upload/3/3b/Bag_Net_Ball_SV_Sprite.png")
}

fun CreateTrainerStash(pokeCoinViewModel: PokeCoinViewModel){
    pokeCoinViewModel.createPokeCoinStash("pokeCoinStash", 0)
}