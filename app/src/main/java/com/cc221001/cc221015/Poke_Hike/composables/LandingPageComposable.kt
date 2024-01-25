package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayLandingPage(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = Color(0, 0, 0, 125))
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
            .fillMaxHeight()
    ) {
        LazyColumn(modifier=Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Welcome, Hiker!", fontSize = 26.sp, color = Color.White
                    )
                }
            }
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    CustomSplitter(h = 2)
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Thank you for choosing our PokeHike!\n",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Before you can jump in, please make sure that:",
                            color = Color.White
                        )
                        BulletText(
                            text = "You have stable internet connection.\n" + "You have accepted all permission requests."
                        )
                        Text(
                            text = "You will create your trainer profile in the next step, and then you will learn more about how PokeHike works right after that.\n",
                            color = Color.White,
                            // textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Have Fun!",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    CustomSplitter(h = 2)
                }
            }

            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    CustomButton(
                        text = "Next Step",
                        onClick = onClick,
                        amount = 300,
                        amount2 = 50,
                        basic = true
                    )
                }
            }
        }
    }
}

