package com.cc221001.cc221015.Poke_Hike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cc221001.cc221015.Poke_Hike.data.PokemonBaseHandler
import com.cc221001.cc221015.Poke_Hike.data.TrainerBaseHandler
import com.cc221001.cc221015.Poke_Hike.ui.theme.MyApplicationTheme
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel

class MainActivity : ComponentActivity() {
    // Database handler for Pokemon trainers.
    private val db = TrainerBaseHandler(this)

    // ViewModel for the main screen.
    private val mainViewModel = MainViewModel(db)

    // Database handler for Pokemon entities.
    private val pdb = PokemonBaseHandler(this)

    // ViewModel for the Pokemon-related view.
    private val pokemonViewModel = PokemonViewModel(pdb)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialize and fetch Pokemon trainers from the database.
                    db.getPokemonTrainers()
                    // Create and display the main view with associated ViewModels.
                    MainView(mainViewModel, pokemonViewModel)
                }
            }
        }
    }
}
