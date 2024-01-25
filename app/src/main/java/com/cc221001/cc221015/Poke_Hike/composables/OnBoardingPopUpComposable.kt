package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.viewModel.OnBoardingViewModel
import kotlinx.coroutines.delay

@Composable
fun DisplayPopUp(onBoardingViewModel: OnBoardingViewModel,title:String, text:String, pageName:String) {
    var showPopUp by remember { mutableStateOf(false) }
    val customFontFamily = FontFamily(Font(R.font.aldrich))
    LaunchedEffect(key1 = true) {
        delay(500)
        showPopUp = true
    }

    if (showPopUp == true) {
        AlertDialog(
            modifier=Modifier.border(2.dp, Color(255,255,255,75),RoundedCornerShape(20.dp)),
            containerColor = Color(0, 0, 0, 200),
            onDismissRequest = {
            }, title = {
                Text(
                    text = title,
                    color = Color.White,
                    fontFamily = customFontFamily,
                )
            }, text = {
                Text(
                    text = text,
                    color = Color.White,
                    fontFamily = customFontFamily,
                )
            }, confirmButton = {
                Surface(
                    color = Color(106, 84, 141, 255), // Set the background color of the surface
                    modifier = Modifier
                        .width(80.dp)
                        .height(50.dp)
                        .clickable(onClick = {
                            onBoardingViewModel.setState(name = pageName, state = true)
                        })
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
                ) {
                    Box(contentAlignment = Alignment.Center, modifier=Modifier.fillMaxWidth()){
                        Text(text="Ok!", color=Color.White, fontFamily = customFontFamily)
                    }
                }
            }
        )
    }
}
