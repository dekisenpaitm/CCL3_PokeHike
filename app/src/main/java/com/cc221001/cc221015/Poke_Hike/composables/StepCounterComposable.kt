package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.StepCounterViewModel

@Composable
fun StepCounterDisplay(viewModel: StepCounterViewModel) {
    val stepCount by viewModel.stepCountLiveData.observeAsState(initial = 0)

    // Display step count in a Text Composable or similar
    Text(text = "Steps: $stepCount")
}
