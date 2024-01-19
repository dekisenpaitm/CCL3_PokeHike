package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        // A Row for displaying the title, with dynamic text based on the 'favorite' flag
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
        item{
            CustomContainer(200, 200, 20, null)
            CustomSplitter(w = 500, h = 2, p = 20 )
            CustomContainerTransparent(w = 500, h = 300, p = 20, null)
            Box(modifier=Modifier.fillMaxWidth(),
                contentAlignment = Center) {
                CustomButton(
                    text = "Create Trainer",
                    onClick = { /*TODO*/ },
                    amount = 340,
                    amount2 = 60
                )
            }
        }
    }
}

@Composable
fun CustomSplitter(w:Int,h:Int,p:Int){
    Surface(modifier = Modifier
        .width(w.dp)
        .height(h.dp)
        .padding(horizontal = p.dp),
        color =Color(255, 255, 255, 50)){}
}
@Composable
fun CustomContainerTransparent(w:Int, h:Int, p:Int, content: @Composable() (() -> Unit?)?){
    Box(modifier=Modifier.fillMaxWidth(),
        contentAlignment = Center)
    {
        Box(
            modifier = Modifier
                .width(w.dp)
                .height(h.dp)
                .padding(p.dp)
                .border(1.dp, Color.White),
        ) {
            if (content != null) {
                content()
            }
        }
    }
}

@Composable
fun CustomContainer(w:Int, h:Int, p:Int, content: @Composable() (() -> Unit?)?){
    Box(modifier=Modifier.fillMaxWidth(),
        contentAlignment = Center)
    {
        Surface(
            modifier = Modifier
                .width(w.dp)
                .height(h.dp)
                .padding(p.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
            color=Color(255,255,255,50)
        ) {
            if (content != null) {
                content()
            }
        }
    }
}

@Composable
fun CustomHeadline(text:String){
    Box(modifier=Modifier.fillMaxWidth(),
        contentAlignment = Center){
        Text(text = text)
    }
}
@Composable
fun CustomButton(text: String, onClick: () -> Unit, amount:Int, amount2:Int) {
    Surface(
        color = Color(255, 255, 255, 50),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(amount.dp)
            .height(amount2.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}
