package com.cc221001.cc221015.Poke_Hike.composables

import com.cc221001.cc221015.Poke_Hike.viewModel.OnBoardingViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel

fun CreatePokeballEntries(pokeballViewModel: PokeballViewModel){


    pokeballViewModel.createPokeball( "SunfireBall", "Clear", "Normal", "Grass", "Fire",10, "https://archives.bulbagarden.net/media/upload/8/8f/Bag_Cherish_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "EnchantedBall", "Clouds", "Fairy", "Fighting", "Psychic",10, "https://archives.bulbagarden.net/media/upload/5/52/Bag_Dive_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "ThunderBall", "Thunderstorm", "Electric", "Flying", "Dragon",10, "https://archives.bulbagarden.net/media/upload/6/6a/Bag_Dream_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "AquaMistBall", "Drizzle", "Water", "Ice", "Electric",10, "https://archives.bulbagarden.net/media/upload/b/b1/Bag_Dusk_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "RainBall",  "Rain", "Water", "Electric", "Bug",10, "https://archives.bulbagarden.net/media/upload/3/3c/Bag_Fast_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "SnowBall", "Snow", "Ice", "Steel", "Water",10, "https://archives.bulbagarden.net/media/upload/4/4c/Bag_Friend_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "MysticBall", "Mist", "Ghost", "Dark", "Fairy",10, "https://archives.bulbagarden.net/media/upload/5/54/Bag_Great_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "SmokeBall",  "Smoke", "Fire", "Poison", "Dark",10, "https://archives.bulbagarden.net/media/upload/4/46/Bag_Heavy_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "HazeBall", "Haze", "Poison", "Flying", "Psychic",10, "https://archives.bulbagarden.net/media/upload/f/f1/Bag_Level_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "RockyDustBall", "Dust", "Ground", "Rock", "Steel",10, "https://archives.bulbagarden.net/media/upload/4/40/Bag_Love_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "GhostlyFogBal",  "Fog", "Ghost", "Dark", "Fairy",10, "https://archives.bulbagarden.net/media/upload/2/26/Bag_Moon_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "QuickSandBall", "Sand", "Ground", "Rock", "Steel",10, "https://archives.bulbagarden.net/media/upload/3/3d/Bag_Nest_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "BlazeAshBall", "Ash", "Fire", "Ground", "Rock",10, "https://archives.bulbagarden.net/media/upload/5/59/Bag_Luxury_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "GustBall", "Squall", "Flying", "Electric", "Ice",10, "https://archives.bulbagarden.net/media/upload/7/72/Bag_Quick_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "WhirlwindBall", "Tornado", "Flying", "Dragon", "Fighting",10, "https://archives.bulbagarden.net/media/upload/4/48/Bag_Strange_Ball_SV_Sprite.png")
    pokeballViewModel.createPokeball( "PokeBall", "All", "All", "All", "All",10, "https://archives.bulbagarden.net/media/upload/0/00/Bag_Pok%C3%A9_Ball_SV_Sprite.png")

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