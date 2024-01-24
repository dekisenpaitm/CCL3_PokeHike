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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cc221001.cc221015.Poke_Hike.domain.PokemonTrainer
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("DiscouragedApi")
@Composable
fun DisplayTrainerProfile(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel, pokeCoinViewModel: PokeCoinViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    var editPopUp by remember{ mutableStateOf(false) }
    var deletePopUp by remember { mutableStateOf(false) }
    var deleteScreenVisible by remember { mutableStateOf(false) }
    var id by rememberSaveable { mutableStateOf(state.value.pokemonTrainers[0].id) }
    var name by rememberSaveable { mutableStateOf(state.value.pokemonTrainers[0].name) }
    var hometown by rememberSaveable { mutableStateOf(state.value.pokemonTrainers[0].hometown) }
    var sprite by rememberSaveable { mutableStateOf(state.value.pokemonTrainers[0].sprite) }
    var currentHometown by remember{mutableStateOf(hometown)}
    var currentName by remember{mutableStateOf(name)}
    var isButtonClicked = false

    mainViewModel.getPokemonTrainer()

    if (deleteScreenVisible==false) {
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
                        CustomContainerTransparent(w = 370, h = 20, p = 0) {
                            Text(text = "NAME:", color = Color.White)
                        }
                        FlowRow(
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            // Box for the value of the trainer property.
                            Box(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                TextField(
                                    value = currentName,
                                    onValueChange = { currentName=it },
                                    label = { Text(text = "Change Name") },
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
                                        .padding(top = 4.dp, bottom = 20.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(
                                            1.dp,
                                            Color(255, 255, 255, 75),
                                            RoundedCornerShape(10.dp)
                                        )
                                )
                            }
                        }
                    }
                    item {

                        CustomContainerTransparent(w = 370, h = 20, p = 0) {
                            Text(text = "HOMETOWN:", color = Color.White)
                        }
                        FlowRow(
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            // Box for the value of the trainer property.
                            Box(
                                modifier = Modifier.weight(1f).fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                TextField(
                                    value = currentHometown,
                                    onValueChange = { currentHometown = it},
                                    label = { Text(text = "Change Hometown") },
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
                                        .padding(top = 4.dp, bottom = 20.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(
                                            1.dp,
                                            Color(255, 255, 255, 75),
                                            RoundedCornerShape(10.dp)
                                        )
                                )
                            }
                        }
                    }
                    item {
                        // Button to delete the trainer.
                        Column(
                            modifier = Modifier.height(140.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            CustomButtonMaxWidth(
                                text = "Save Changes",
                                onClick = { editPopUp = true },
                                height = 50,
                                true
                            )
                            Surface(
                                color = Color(58, 42, 75, 255),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(
                                        2.dp,
                                        Color(255, 255, 255, 75),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable(onClick = {
                                        deletePopUp = true
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
                }
            }
        }
    }else {
        DisplayRemoveLoadingPage()
    }

    if(editPopUp){
        DisplayTrainerPopUp(
            title = "Save Changes?" ,
            text = "Do you really want to save your changes?",
            buttonAcceptText = "SAVE",
            buttonDismissText = "CANCEL",
            onAcceptClick={
                mainViewModel.savePokemonTrainer(PokemonTrainer(id, currentName, currentHometown, sprite))
                editPopUp=false
            },
            onDismissClick={
                editPopUp=false
            },
            onDismiss={
                editPopUp=false
            })
    }

    if(deletePopUp){
        DisplayTrainerPopUp(
            title = "Delete Trainer?" ,
            text = "Do you really want to delete your Trainer? This can't be undone!",
            buttonAcceptText = "DELETE",
            buttonDismissText = "CANCEL",
            onAcceptClick={
                deletePopUp=false
                deleteScreenVisible = true
                android.os
                    .Handler()
                    .postDelayed({
                        pokeCoinViewModel.deletePokeCoinStash(
                            pokeCoinViewModel.pokeCoinViewState.value.pokeCoin
                        );
                        pokemonViewModel.resetPokemonDatabase()
                        if (!isButtonClicked && state.value.pokemonTrainers.isNotEmpty()) {
                            isButtonClicked = true
                            mainViewModel.deletePokemonTrainer(state.value.pokemonTrainers[0])
                            android.os
                                .Handler()
                                .postDelayed({
                                    isButtonClicked =
                                        false; deleteScreenVisible =
                                    false
                                }, 500)
                        }
                    }, 2000)
            },
            onDismissClick={
                deletePopUp=false
            },
            onDismiss={
                deletePopUp=false
            })
    }
}

@Composable
fun DisplayTrainerPopUp(title:String, text:String, buttonAcceptText:String, buttonDismissText:String, onAcceptClick: ()->Unit, onDismiss: ()->Unit, onDismissClick: ()->Unit) {
    androidx.compose.material3.AlertDialog(
        containerColor = Color(16, 0, 25, 200),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                color = Color.White
            )
        }, text = {
            Text(
                text = text,
                color = Color.White
            )
        }, confirmButton = {
            Surface(
                color = Color(106, 84, 141, 255), // Set the background color of the surface
                modifier = Modifier
                    .width(80.dp)
                    .height(50.dp)
                    .clickable(onClick = onAcceptClick)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(text = buttonAcceptText, color=Color.White)
                }
            }
        },
        dismissButton={
            Surface(
                color = Color(58, 42, 75, 255), // Set the background color of the surface
                modifier = Modifier
                    .width(80.dp)
                    .height(50.dp)
                    .clickable(onClick = onDismissClick)
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(text = buttonDismissText, color=Color.White)
                }
            }
        }
    )
}


