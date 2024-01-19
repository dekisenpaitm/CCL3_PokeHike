package com.cc221001.cc221015.Poke_Hike.composables

import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel

fun CreatePokeballEntries(pokeballViewModel: PokeballViewModel){


    pokeballViewModel.createPokeball( "SunfireBall", "Clear", "Normal", "Grass", "Fire",10, "https://archives.bulbagarden.net/media/upload/4/44/Dream_Fast_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "EnchantedBall", "Clouds", "Fairy", "Fighting", "Psychic",10, "https://archives.bulbagarden.net/media/upload/2/27/Dream_Dream_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "ThunderBall", "Thunderstorm", "Electric", "Flying", "Dragon",10, "https://archives.bulbagarden.net/media/upload/1/19/Dream_Level_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "AquaMistBall", "Drizzle", "Water", "Ice", "Electric",10, "https://archives.bulbagarden.net/media/upload/9/9a/Dream_Dive_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "RainBall",  "Rain", "Water", "Electric", "Bug",10, "https://archives.bulbagarden.net/media/upload/a/a0/Dream_Net_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "SnowBall", "Snow", "Ice", "Steel", "Water",10, "https://archives.bulbagarden.net/media/upload/6/64/Dream_Premier_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "MysticBall", "Mist", "Ghost", "Dark", "Fairy",10, "https://archives.bulbagarden.net/media/upload/9/95/Dream_Master_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "SmokeBall",  "Smoke", "Fire", "Poison", "Dark",10, "https://archives.bulbagarden.net/media/upload/c/c9/Bag_Heavy_Ball_LA_Sprite.png")
    pokeballViewModel.createPokeball( "HazeBall", "Haze", "Poison", "Flying", "Psychic",10, "https://archives.bulbagarden.net/media/upload/4/48/Bag_Strange_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "RockyDustBall", "Dust", "Ground", "Rock", "Steel",10, "https://archives.bulbagarden.net/media/upload/a/a8/Dream_Ultra_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "GhostlyFogBal",  "Fog", "Ghost", "Dark", "Fairy",10, "https://archives.bulbagarden.net/media/upload/2/22/Dream_Moon_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "QuickSandBall", "Sand", "Ground", "Rock", "Steel",10, "https://archives.bulbagarden.net/media/upload/0/09/Dream_Park_Ball_Sprite.png")
    pokeballViewModel.createPokeball( "BlazeAshBall", "Ash", "Fire", "Ground", "Rock",10, "https://archives.bulbagarden.net/media/upload/2/20/Bag_Ultra_Ball_LA_Sprite.png")
    pokeballViewModel.createPokeball( "GustBall", "Squall", "Flying", "Electric", "Ice",10, "https://archives.bulbagarden.net/media/upload/c/cc/Bag_Feather_Ball_LA_Sprite.png")
    pokeballViewModel.createPokeball( "WhirlwindBall", "Tornado", "Flying", "Dragon", "Fighting",10, "https://archives.bulbagarden.net/media/upload/6/65/Dream_Beast_Ball_Sprite.png")

}

fun CreateTrainerStash(pokeCoinViewModel: PokeCoinViewModel){
    pokeCoinViewModel.createPokeCoinStash("pokeCoinStash", 0)
}