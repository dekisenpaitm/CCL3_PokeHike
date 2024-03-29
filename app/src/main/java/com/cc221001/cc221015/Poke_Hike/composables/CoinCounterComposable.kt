package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel

@Composable
fun CoinCounterDisplay(viewModel: PokeCoinViewModel) {
    val stepCount by viewModel.pokeCoinViewState.collectAsState()
    viewModel.getPokeCoins()
    // Display step count in a Text Composable or similar
    Text(text = "Steps: ${stepCount.pokeCoin.amount}")
}
