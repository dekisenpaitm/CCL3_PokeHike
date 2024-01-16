package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.cc221001.cc221015.Poke_Hike.R
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

// Composable function for the main screen of the app.
@Composable
fun mainScreen(mainViewModel: MainViewModel){
    // Displaying text, potentially a placeholder for more dynamic content.
    Text(text = "Thank you for creating your trainerprofile. Feel free to discover the features of the app. On the 'heart' icon you can find your favorite pokemon." +
            "To add Pokemon to the list click the 'list' icon in the navigation bar. Now you have a full list of Pokemon (currently restricted to 151) by pressing the 'heart' at the" +
            " right side of the entry the Pokemon will be add to your favs. To Remove pokemon from your favs just navigate to the 'heart' icon and click the 'heart' icon " +
            "on the entry you want to remove. If you wanna change anything regarding your profile or even deleting you can find it under the 'profile' tab.")

}

// Helper Composable function for displaying the background.
@Composable
fun SetBackgroundMain() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.hills_background),
            contentDescription = "Login_Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

