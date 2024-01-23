package com.cc221001.cc221015.Poke_Hike.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.domain.PokemonTrainer
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel
import java.util.logging.Handler

// Composable function to display values related to a Pokemon Trainer.
@Composable
fun TrainerValues(mainViewModel: MainViewModel) {
    // Retrieves declared fields from the PokemonTrainer class for reflection.
    val pokemonTrainer = PokemonTrainer::class.java.declaredFields
    // Sorts the attributes based on the length of their names.
    val sortedAttributes = pokemonTrainer.sortedBy { it.name.length }

    // LazyColumn is used for efficient, scrollable lists.
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Iterates over the sorted attributes to create list items.
        sortedAttributes.forEach { pokemonTrainerField ->
            // Ensures that the field is accessible.
            pokemonTrainerField.isAccessible
            // Filter out fields with specific names.
            if (!pokemonTrainerField.name.contains("stable", false)
                && !pokemonTrainerField.name.contains("sprite", false)) {
                // Calls TrainerItem Composable for each field.
                TrainerItem(pokemonTrainerField.name, mainViewModel)
            }
        }
    }
}

// This function uses reflection to dynamically create UI components based on the fields of the PokemonTrainer class.
// It filters out certain fields and displays the rest using the TrainerItem Composable.


// Composable function to display individual trainer items, using the ExperimentalLayoutApi.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrainerItem(trainerValue: String, mainViewModel: MainViewModel) {
    // Collects the state from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()

    // Accesses a specific property of the PokemonTrainer class using reflection.
    val trainerProperty = PokemonTrainer::class.java.getDeclaredField(trainerValue)
    trainerProperty.isAccessible = true

    // FlowRow is used for arranging items in a row that can wrap onto multiple lines.
    // Box for the label of the trainer property.
    CustomContainerTransparent(w = 370, h = 20, p = 0 ) {
            Text(text = trainerValue.uppercase() + ":", color = Color.White)
    }
    Surface(
        modifier = Modifier
            .width(400.dp)
            .padding(vertical = 4.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
        color = Color(255, 255, 255, 50)
    ) {
        FlowRow(verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            ) {
            // Box for the value of the trainer property.
            Box(modifier=Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Text(
                    modifier=Modifier.padding(horizontal = 10.dp),
                    text = "${trainerProperty.get(state.value.pokemonTrainers[0])}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}


// This function utilizes reflection to dynamically access properties of the PokemonTrainer class.
// It displays each property as a text label with its corresponding value in a flow layout.


// Suppresses lint warnings for discouraged API usage.
@SuppressLint("DiscouragedApi")
// Composable function to display the profile of a Pokemon Trainer.
@Composable
fun DisplayTrainerProfile(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel, pokeCoinViewModel: PokeCoinViewModel) {
    // Collects the current state from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()
    var isButtonClicked = false

    if(state.value.pokemonTrainers.isNotEmpty()) {
        // A Column layout to vertically arrange elements.
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0, 0, 0, 125), RoundedCornerShape(10.dp))
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        ) {
            // A LazyColumn for efficiently displaying a scrollable list.
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(20.dp, 20.dp, 20.dp, 0.dp)
            ) {
                // Displaying the trainer's image.
                item {
                    if (state.value.pokemonTrainers.isNotEmpty()) {
                        Box() {
                            // Retrieve the resource ID for the trainer's sprite.

                            val resourceId = LocalContext.current.resources.getIdentifier(
                                state.value.pokemonTrainers[0].sprite,
                                "drawable",
                                LocalContext.current.packageName
                            )
                            // Construct the image URL.
                            val imageUrl =
                                "android.resource://${LocalContext.current.packageName}/$resourceId"
                            val painter = rememberAsyncImagePainter(model = imageUrl)
                            // Display the image.
                            Surface(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        2.dp,
                                        Color(255, 255, 255, 75),
                                        RoundedCornerShape(10.dp)
                                    ),
                                color = Color(255, 255, 255, 50)
                            ) {
                                Image(
                                    painter = painter,
                                    contentDescription = "Pokemon Image",
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                        .padding(10.dp)
                                )
                            }
                        }
                    }
                }
                item {
                    // Display trainer's attributes using TrainerValues composable.
                    TrainerValues(mainViewModel = mainViewModel)
                }
                item {
                    // Button to delete the trainer.
                    Column(
                        modifier = Modifier.height(140.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomButton(
                            text = "Update Trainer",
                            onClick = { mainViewModel.editPokemonTrainer(state.value.pokemonTrainers[0]) },
                            amount = 320,
                            amount2 = 50,
                            true
                        )
                        Surface(
                            color = Color(58, 42, 75, 255),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
                                .clickable(onClick = {
                                    if(!isButtonClicked && state.value.pokemonTrainers.isNotEmpty()) {
                                        isButtonClicked=true
                                        mainViewModel.deletePokemonTrainer(state.value.pokemonTrainers[0]);
                                        pokeCoinViewModel.deletePokeCoinStash(pokeCoinViewModel.pokeCoinViewState.value.pokeCoin);
                                        pokemonViewModel.resetPokemonDatabase()
                                        android.os.Handler()
                                            .postDelayed({ isButtonClicked = false }, 2000)
                                    }
                                })
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "Delete Trainer",
                                    modifier = Modifier.padding(8.dp),
                                    color = Color.White
                                )
                            }
                        }

                    }
                }
                item {
                    Column {
                        editTrainerModel(mainViewModel)
                    }
                }
            }
        }
    }

    // Call to the editTrainerModel function, possibly for editing trainer detail
}

// This function is designed to display detailed information about a Pokemon Trainer,
// including an image, attributes, and options to update or delete the trainer.

// Opt-in for Experimental Material3 API and define the Composable function for editing a trainer's model.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editTrainerModel(mainViewModel: MainViewModel) {
    // Collect state from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()

    // Check if the dialog state is open.
    if (state.value.openDialog) {
        // Remember saveable states for the trainer's properties.
        var id by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.id) }
        var name by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.name) }
        var gender by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.hometown) }
        var sprite by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.sprite) }

        // AlertDialog to show the editing interface.
        AlertDialog(
            onDismissRequest = { mainViewModel.dismissDialog() }, // Handle dialog dismissal.
            text = {
                // Column layout for the text fields.
                Column {
                    // Text field for editing the username.
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = name,
                        onValueChange = { newText -> name = newText },
                        label = { Text(text = "Change Username") }
                    )
                    // Text field for editing the gender.
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = gender,
                        onValueChange = { newText -> gender = newText },
                        label = { Text(text = "Change Gender") }
                    )
                }
            },
            confirmButton = {
                // Button for saving changes.
                Button(onClick = {
                    mainViewModel.savePokemonTrainer(PokemonTrainer(id, name, gender, sprite))
                }) {
                    Text(stringResource(R.string.editmodal_button_save))
                }
            }
        )
    }
}

// This function provides an interface for editing a Pokemon Trainer's details, using an AlertDialog for input.
// It uses saveable states to preserve data during configuration changes and interacts with the MainViewModel for data handling.

