package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel

// Helper Composable function for displaying errors.
@Composable
fun ErrorScreen(){
    Text(text = "Looks like you didn't create your user yet, please make sure to create one before using PokeHike!")
}

// Helper Composable function for displaying text.
@Composable
fun TextBox(text:String){
    Text(text = text)
}

@Composable
fun mainScreen(mainViewModel: MainViewModel){
// Using a Column to layout elements vertically.
    Column() {
        // A Row for displaying the title, with dynamic text based on the 'favorite' flag.
        Row(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier
                .fillMaxSize()
                .align(alignment = CenterVertically)) {
                Box(modifier = Modifier.fillMaxSize()) {
                    val text = "Home"
                    Text(text = text, fontSize = 40.sp, color = Color(66,66,66,255))
                }
                val text = "Home"
                Text(text = text, fontSize = 100.sp, color = Color(66, 66, 66, 125))
            }
        }

        // A Row to display the list of Pokemon.
        Row (modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd= 20.dp, bottomEnd = 0.dp, bottomStart=0.dp))){
            // Calling PokemonList Composable to display the actual list.
            ContentList()
        }
    }
}

@Composable
fun ContentList() {
    // LazyColumn is used for efficiently displaying a list that can be scrolled.
    // It only renders the items that are currently visible on screen.
    LazyColumn (modifier= Modifier
        .background(color = Color(0, 0, 0, 125))
        .padding(top = 20.dp)
        .fillMaxSize()
    ){
    }
}

