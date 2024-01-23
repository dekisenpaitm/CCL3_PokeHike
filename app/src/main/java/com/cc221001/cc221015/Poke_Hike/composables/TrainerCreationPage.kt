package com.cc221001.cc221015.Poke_Hike.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cc221001.cc221015.Poke_Hike.domain.PokemonTrainer
import com.cc221001.cc221015.Poke_Hike.domain.StepCounter
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeballViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.StepCounterViewModel

// Suppresses lint warnings for using discouraged or experimental APIs.
@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
// Defines a Composable function for the landing page.
@Composable
fun landingPage(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel, pokeballViewModel: PokeballViewModel, stepCounterViewModel: StepCounterViewModel, pokeCoinViewModel: PokeCoinViewModel) {
    // State for tracking the expansion status of the dropdown menu.

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp)
        .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
        .background(Color(0,0,0,125), RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally){
        item{ LandingPageContent(
            mainViewModel = mainViewModel,
            pokemonViewModel = pokemonViewModel,
            pokeballViewModel = pokeballViewModel,
            stepCounterViewModel = stepCounterViewModel,
            coinViewModel = pokeCoinViewModel)}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LandingPageContent(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel, pokeballViewModel:PokeballViewModel, stepCounterViewModel:StepCounterViewModel, coinViewModel: PokeCoinViewModel){

    var isExpanded by remember { mutableStateOf(false) }
    // State for storing the index of the selected trainer's image.
    var selectedTrainerIndex by remember { mutableStateOf("") }
    // State for storing the selected trainer's name.
    var trainerName by remember { mutableStateOf("Pick Trainer") }

    // List of trainer image resource names.
    val trainerImageResources = listOf(
        "trainer1",
        "trainer2",
        "trainer3",
        "trainer4",
        "trainer5",
        "trainer6",
        "trainer7",
        "trainer8",
        "trainer9",
        "trainer10",
        "trainer11"
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Async image painter for loading the trainer's image.
            Surface(
                modifier = Modifier
                    .width(180.dp)
                    .height(180.dp)
                    .padding(0.dp,20.dp,0.dp,0.dp)
                    .clip(RoundedCornerShape(20.dp,20.dp,0.dp,0.dp))
                    .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
                color = Color(255, 255, 255, 50)
            ) {
                val painter = rememberAsyncImagePainter(model = selectedTrainerIndex)
                Image(
                    painter = painter,
                    contentDescription = "Pokemon Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .padding(20.dp)
                )
            }
            // Exposed dropdown menu box for selecting a trainer.
            ExposedDropdownMenuBox(
                modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                // TextField for displaying the selected trainer's name.
                TextField(
                    value = trainerName.replaceFirst(
                        trainerName[0].toString(),
                        trainerName[0].uppercaseChar().toString()
                    ),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color(255, 255, 255, 75)
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
                )
                // Dropdown menu for selecting a trainer.
                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(Color(255, 255, 255, 50))
                        .clip(RoundedCornerShape(10.dp)),
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                ) {
                    // Iterating over the list of trainer image resources.
                    for (i in trainerImageResources) {
                        var currentTrainerName = i
                        val resourceId = LocalContext.current.resources.getIdentifier(
                            i,
                            "drawable",
                            LocalContext.current.packageName
                        )
                        val imageUrl =
                            "android.resource://${LocalContext.current.packageName}/$resourceId"
                        // DropdownMenuItem for each trainer.
                        DropdownMenuItem(
                            modifier = Modifier.background(Color(255, 255, 255, 50)),
                            text = { Text(text = currentTrainerName) },
                            onClick = {
                                selectedTrainerIndex = imageUrl; trainerName =
                                currentTrainerName; isExpanded = false;
                            },
                        )
                    }
                }
            }

            // State and TextField for inputting the trainer's name.
            var name by remember { mutableStateOf("") }
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(
                        255,
                        255,
                        255,
                        75
                    ),
                    textColor = Color.White
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),

                )
            // State and TextField for inputting the trainer's gender.
            var gender by remember { mutableStateOf("") }
            TextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text(text = "Hometown", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(
                        255,
                        255,
                        255,
                        75
                    ),
                    textColor = Color.White
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),

                )
            Box(modifier = Modifier.padding(20.dp)) {
                CustomButton(text = "Create Trainer", onClick = {
                    CreatePokeballEntries(pokeballViewModel)
                    CreateTrainerStash(coinViewModel)
                    stepCounterViewModel.createStepCounter((StepCounter(0,0)));
                    mainViewModel.save(PokemonTrainer(null, name, gender, trainerName))
                    mainViewModel.getPokemonTrainer();
                    pokemonViewModel.loadPokemons();
                }, amount = 300, amount2 = 50, true)
            }
        }
    }
}
