package com.cc221001.cc221015.Poke_Hike.composables

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.domain.PokemonTrainer
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel

@RequiresApi(Build.VERSION_CODES.Q)
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
    val customFontFamily = FontFamily(Font(R.font.aldrich))
    val context = LocalContext.current
    val maxHomeLength = 25
    val maxLength = 10

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
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(20.dp, 20.dp, 20.dp, 0.dp)
                ) {
                    // Displaying the trainer's image.

                    item {
                        if (state.value.pokemonTrainers.isNotEmpty()) {
                            Box(modifier=Modifier.padding(bottom=20.dp)) {
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
                    item{
                        Box(modifier=Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                            CustomSplitter(h = 2)
                        }
                    }
                    item {
                        CustomContainerTransparent(w = 370, h = 20, p = 0) {
                            Text(text = "Name:", color = Color.White, fontFamily = customFontFamily)
                        }
                        FlowRow(
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            // Box for the value of the trainer property.
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                TextField(
                                    value = currentName,
                                    onValueChange = { if (it.length <= maxLength) currentName = it
                                    else Toast.makeText(context, "Cannot be more than 10 Characters", Toast.LENGTH_SHORT).show() },
                                    textStyle = TextStyle(
                                        fontFamily = customFontFamily),
                                    //label = { Text(text = "Click to change name") },
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
                            Text(text = "Hometown:", color = Color.White, fontFamily = customFontFamily)
                        }
                        FlowRow(
                            verticalArrangement = Arrangement.Center,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            // Box for the value of the trainer property.
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                TextField(
                                    value = currentHometown,
                                    onValueChange = { if (it.length <= maxHomeLength) currentHometown = it
                                    else Toast.makeText(context, "Cannot be more than 25 Characters", Toast.LENGTH_SHORT).show()},
                                    textStyle = TextStyle(
                                        fontFamily = customFontFamily),
                                    //label = { Text(text = "Click to change hometown") },
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color(
                                            255,
                                            255,
                                            255,
                                            75
                                        ),
                                        textColor = Color.White,
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
                    item{
                        Box(modifier=Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                            CustomSplitter(h = 2)
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
                            CustomButtonMaxWidth(
                                text = "Delete Trainer",
                                onClick = { deletePopUp = true },
                                height = 50,
                                false
                            )
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
            buttonAcceptText = "Save",
            buttonDismissText = "Cancel",
            onAcceptClick={
                if(currentName.isNotEmpty()&&currentHometown.isNotEmpty()){
                mainViewModel.savePokemonTrainer(PokemonTrainer(id, currentName, currentHometown, sprite))
                    Toast.makeText(context, "Changes have been saved!", Toast.LENGTH_SHORT).show()
                editPopUp=false}
                else {
                    Toast.makeText(context, "Name and Hometown can't be empty!", Toast.LENGTH_SHORT).show()
                }
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
            buttonAcceptText = "Delete",
            buttonDismissText = "Cancel",
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
    val customFontFamily = FontFamily(Font(R.font.aldrich))
    AlertDialog(
        containerColor = Color(0, 0, 0, 200),
        modifier=Modifier.clip(RoundedCornerShape(10.dp)).border(2.dp,Color(255,255,255,75),
            RoundedCornerShape(20.dp)
        ),
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                color = Color.White,
                        fontFamily = customFontFamily
            )
        }, text = {
            Text(
                text = text,
                color = Color.White,
                fontFamily = customFontFamily
            )
        }, confirmButton = {
            Surface(
                color = Color(106, 84, 141, 255), // Set the background color of the surface
                modifier = Modifier
                    .width(80.dp)
                    .height(50.dp)
                    .clickable(onClick = onAcceptClick)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(text = buttonAcceptText, color=Color.White, fontFamily = customFontFamily)
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
                    Text(text = buttonDismissText, color=Color.White, fontFamily = customFontFamily)
                }
            }
        }
    )
}


