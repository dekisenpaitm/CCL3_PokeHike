package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayLandingPage(onClick: ()->Unit) {
    Row (modifier = Modifier
        .clip(RoundedCornerShape(topStart = 20.dp, topEnd= 20.dp, bottomEnd = 0.dp, bottomStart=0.dp))){
        Column(modifier= Modifier
            .background(color = Color(0, 0, 0, 125))
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
            .fillMaxSize()){
            LazyColumn ()
            {
                item {
                    Box(contentAlignment = Alignment.Center,
                        modifier=Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
                        Text(
                            text="Welcome Hiker!",
                            fontSize=26.sp,
                            color=Color.White
                        )
                    }
                }
                item {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
                        CustomSplitter(h = 2)
                    }
                }

                item {
                    Box(contentAlignment = Alignment.Center,
                        modifier=Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
                        Text(text = "Thank you for choosing our app!\n" +
                                "\n" +
                                "Before you can jump into PokeHike please make sure that:\n" +
                                "\n" +
                                "#you have an available internet connection\n" +
                                "\n" +
                                "#you accepted all permission requests\n" +
                                "\n" +
                                "#you created a trainer profile in the next step\n" +
                                "\n" +
                                "How our app works will be explained on the home tab after you created your trainer!\n" +
                                "\n" +
                                "Enjoy!",
                            color=Color.White
                        )
                    }
                }

                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        CustomSplitter(h = 2)
                    }
                }

                item {
                    Box(contentAlignment = Alignment.Center,
                        modifier=Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
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

}
