package com.cc221001.cc221015.Poke_Hike.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cc221001.cc221015.Poke_Hike.domain.PokemonTrainer
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokemonViewModel

// Suppresses lint warnings for using discouraged or experimental APIs.
@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
// Defines a Composable function for the landing page.
@Composable
fun landingPage(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel) {
    // State for tracking the expansion status of the dropdown menu.
    var isExpanded by remember { mutableStateOf(false) }
    // State for storing the index of the selected trainer's image.
    var selectedTrainerIndex by remember { mutableStateOf("") }
    // State for storing the selected trainer's name.
    var trainerName by remember { mutableStateOf("") }

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
    // Column layout for vertical alignment of UI elements.
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Async image painter for loading the trainer's image.
        Surface(
            modifier = Modifier
                .width(260.dp)
                .height(260.dp)
                .padding(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
            color= Color(255,255,255,50)
        ) {
            val painter = rememberAsyncImagePainter(model = selectedTrainerIndex)
            Image(
                painter = painter,
                contentDescription = "Pokemon Image",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(30.dp)
            )
        }
        // Exposed dropdown menu box for selecting a trainer.
        ExposedDropdownMenuBox(
            modifier=Modifier.clip(RoundedCornerShape(10.dp)),
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            // TextField for displaying the selected trainer's name.
            TextField(
                value = trainerName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
            )
            // Dropdown menu for selecting a trainer.
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                // Iterating over the list of trainer image resources.
                for (i in trainerImageResources) {
                    var currentTrainerName = i
                    val resourceId = LocalContext.current.resources.getIdentifier(
                        i,
                        "drawable",
                        LocalContext.current.packageName
                    )
                    val imageUrl = "android.resource://${LocalContext.current.packageName}/$resourceId"
                    // DropdownMenuItem for each trainer.
                    DropdownMenuItem(
                        text = { TextBox(text = currentTrainerName) },
                        onClick = { selectedTrainerIndex = imageUrl; trainerName = currentTrainerName; isExpanded = false; })
                }
            }
        }

        // State and TextField for inputting the trainer's name.
        var name by remember { mutableStateOf("") }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(10.dp)),
        )
        // State and TextField for inputting the trainer's gender.
        var gender by remember { mutableStateOf("") }
        TextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Hometown") },
            modifier = Modifier
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color(255, 255, 255, 75)),
        )
        Box(modifier=Modifier.padding(20.dp)) {
            CustomButton(text = "Create Trainer", onClick = {
                mainViewModel.save(PokemonTrainer(null, name, gender, trainerName))
                mainViewModel.getPokemonTrainer(); pokemonViewModel.loadPokemons()
            }, amount = 300, amount2 = 60)
        }
    }
}
