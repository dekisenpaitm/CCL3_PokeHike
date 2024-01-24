package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221001.cc221015.Poke_Hike.R

@Composable
fun DisplayLoadingPage(){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .background(color = Color(0, 0, 0, 125))
            .padding(20.dp,20.dp,20.dp,0.dp)
            .fillMaxSize()) {
        Image(
            painter = painterResource(
                id = R.drawable.pokehikelogo
            ),
            contentDescription = "Login_Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(200.dp),
        )
        Box(contentAlignment = Alignment.Center, modifier=Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 10.dp)){
            Text(text = "PokeHike", fontSize = 40.sp, color = Color.White)
        }
        Box(contentAlignment = Alignment.Center, modifier=Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 10.dp)){
            Text(text = "We're setting the stage for your PokeHike!", fontSize = 16.sp, color = Color.White, textAlign = TextAlign.Center)
        }

    }
}
