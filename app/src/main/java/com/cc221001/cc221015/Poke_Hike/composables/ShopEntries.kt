package com.cc221001.cc221015.Poke_Hike.composables

import com.cc221001.cc221015.Poke_Hike.viewModel.OnBoardingViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel

fun CreatePokeballEntries(pokeballViewModel: PokeballViewModel){


    pokeballViewModel.createPokeball( "SunfireBall", "Clear", "Normal", "Grass", "Fire",1000, "https://archives.bulbagarden.net/media/upload/8/8f/Bag_Cherish_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "WanderBall", "Clouds", "Fairy", "Fighting", "Psychic",1000, "https://archives.bulbagarden.net/media/upload/6/6a/Bag_Dream_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "ThunderBall", "Thunderstorm", "Electric", "Flying", "Dragon",1000, "https://archives.bulbagarden.net/media/upload/5/52/Bag_Dive_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "AquaBall", "Drizzle", "Water", "Ice", "Electric",1000, "https://archives.bulbagarden.net/media/upload/b/b1/Bag_Dusk_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "PourBall",  "Rain", "Water", "Electric", "Bug",1000, "https://archives.bulbagarden.net/media/upload/3/3c/Bag_Fast_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "SnowBall", "Snow", "Ice", "Steel", "Water",1000, "https://archives.bulbagarden.net/media/upload/4/4c/Bag_Friend_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "MysticBall", "Mist", "Ghost", "Dark", "Fairy",1000, "https://archives.bulbagarden.net/media/upload/4/40/Bag_Love_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "SmokeBall",  "Smoke", "Fire", "Poison", "Dark",1000, "https://archives.bulbagarden.net/media/upload/4/46/Bag_Heavy_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "HazeBall", "Haze", "Poison", "Flying", "Psychic",1000, "https://archives.bulbagarden.net/media/upload/f/f1/Bag_Level_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "RockyBall", "Dust", "Ground", "Rock", "Steel",1000, "https://archives.bulbagarden.net/media/upload/5/54/Bag_Great_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "GhostlyBal",  "Fog", "Ghost", "Dark", "Fairy",1000, "https://archives.bulbagarden.net/media/upload/2/26/Bag_Moon_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "GrainBall", "Sand", "Ground", "Rock", "Steel",1000, "https://archives.bulbagarden.net/media/upload/3/3d/Bag_Nest_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "BlazeBall", "Ash", "Fire", "Ground", "Rock",1000, "https://archives.bulbagarden.net/media/upload/5/59/Bag_Luxury_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "GustBall", "Squall", "Flying", "Electric", "Ice",1000, "https://archives.bulbagarden.net/media/upload/7/72/Bag_Quick_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "WhirlBall", "Tornado", "Flying", "Dragon", "Fighting",1000, "https://archives.bulbagarden.net/media/upload/4/48/Bag_Strange_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "PokeBall", "All", "All", "All", "All",2000, "https://archives.bulbagarden.net/media/upload/0/00/Bag_Pok%C3%A9_Ball_SV_Sprite.png")

}

fun CreateTrainerStash(pokeCoinViewModel: PokeCoinViewModel){
    pokeCoinViewModel.createPokeCoinStash(id=1,"pokeCoinStash", 0)
}

fun CreateOnBoarding(onBoardingViewModel: OnBoardingViewModel){
    onBoardingViewModel.createOnboardingViewState(name="homePage")
    onBoardingViewModel.createOnboardingViewState(name="weatherPage")
    onBoardingViewModel.createOnboardingViewState(name="favPage")
    onBoardingViewModel.createOnboardingViewState(name="listPage")
    onBoardingViewModel.createOnboardingViewState(name="shopPage")
    onBoardingViewModel.createOnboardingViewState(name="profilePage")
}