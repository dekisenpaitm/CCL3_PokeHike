package com.cc221001.cc221015.Poke_Hike.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cc221001.cc221015.Poke_Hike.R
import com.cc221001.cc221015.Poke_Hike.service.dto.CurrentWeather
import com.cc221001.cc221015.Poke_Hike.viewModel.MainViewModel
import com.cc221001.cc221015.Poke_Hike.viewModel.PokeCoinViewModel
import com.cc221001.cc221015.Poke_Hike.views.Screen

// Helper Composable function for displaying errors.
@Composable
fun ErrorScreen() {
    Text(text = "Looks like you didn't create your user yet, please make sure to create one before using PokeHike!")
}


@Composable
fun mainScreen(
    mainViewModel: MainViewModel,
    pokeCoinViewModel: PokeCoinViewModel,
    navController: NavHostController
) {
// Using a Column to layout elements vertically.
    Column() {
        // A Row for displaying the title, with dynamic text based on the 'favorite' flag
        // A Row to display the list of Pokemon.
        Row(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
        ) {
            // Calling PokemonList Composable to display the actual list.
            ContentList(pokeCoinViewModel = pokeCoinViewModel, navController = navController)
        }
    }
}

@Composable
fun ContentList(pokeCoinViewModel: PokeCoinViewModel, navController: NavHostController) {
    // LazyColumn is used for efficiently displaying a list that can be scrolled.
    // It only renders the items that are currently visible on screen.
    LazyColumn(
        modifier = Modifier
            .background(color = Color(0, 0, 0, 125))
            .padding(top = 4.dp)
            .fillMaxSize()
    ) {
        item {

            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0, 0, 0, 125), RoundedCornerShape(10.dp))
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, bottom = 20.dp, end = 20.dp)
                ) {
                    CustomHeadline(text = "Welcome Back!")
                    CustomContainerWelcome()
                    CustomHeadline(text = "Current Coins")
                    CustomContainerCoins(
                        pokeCoinViewModel = pokeCoinViewModel,
                        navController = navController
                    )
                    CustomHeadline(text = "Reminders")
                    CustomContainerReminder()
                }
            }
        }
    }
}

@Composable
fun CustomSplitter(h: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(h.dp),
        color = Color(255, 255, 255, 50)
    ) {}
}

@Composable
fun CustomContainerTransparent(w: Int, h: Int, p: Int, content: @Composable() (() -> Unit?)?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Center
    )
    {
        Box(
            modifier = Modifier
                .width(w.dp)
                .height(h.dp)
                .padding(p.dp)
        ) {
            if (content != null) {
                content()
            }
        }
    }
}

@Composable
fun CustomContainerWelcome() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Center
    )
    {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
            color = Color(255, 255, 255, 50),
        ) {
            Column()
            {
                Text(
                    text = "Have you caught your favourite Pokemon yet? \n\n" +
                            "If you forgot something, scroll down for reminders at the bottom of Home Page!",
                    color = Color.White,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    ),
                )
            }
        }
    }
}


@Composable
fun CustomContainerCoins(pokeCoinViewModel: PokeCoinViewModel, navController: NavHostController) {
    val currentCoins by pokeCoinViewModel.pokeCoinViewState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 0.dp, end = 0.dp, bottom = 0.dp)
    ) {
        // Left Box
        Box(
            modifier = Modifier
                .weight(0.74f)
                .height(80.dp)
                .background(Color(255, 255, 255, 50), RoundedCornerShape(10.dp))
                .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.30f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pokecoin),
                        contentDescription = "PokeCoin Icon",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.67f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "${currentCoins.pokeCoin.amount}\n",
                        color = Color.White,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 20.dp,
                            bottom = 0.dp
                        ),
                        fontSize = 28.sp,
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .width(8.dp)
                .weight(0.02f)
        )

        // Right Box (Button)
        Box(
            contentAlignment = Center,
            modifier = Modifier
                .weight(0.26f)
                .fillMaxHeight()
            //.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            CustomButtonMaxWidth(
                text = "SPEND",
                onClick = { navController.navigate(Screen.Shop.route) },
                height = 80,
                true
            )
        }
    }
}

@Composable
fun CustomContainerReminder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Center
    )
    {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp)),
            color = Color(255, 255, 255, 50),
        ) {
            Column()
            {
                BulletText(
                    text = "You need coins to buy Pokemon. Hike together with your phone to collect coins.\n"
                            + "One Step equals One Coin!\n"
                            + "Spend coins on Pokeballs in shop to collect all the Pokemon!\n"
                            + "Don't forget that Weather affects the types of Pokeballs you will see in the shop. \n"
                            + "A normal Pokeball can give you a pokemon of any type, while special Weather Pokeballs will give you Pokemon depending on weather.\n"
                            + "The Shop always provides details on the current Weather Pokeball.\n"
                            + "When viewing the Pokedex or Owned Pokemon, you can press the heart to add Pokemon to your favourites list.\n"
                            + "When viewing Pokemons in your Collection or in Pokedex, you can see a tick. A grey tick means that you do not own this Pokemon, a white tick means that you do own it.\n"
                            + "Numbers above Items in the Shop indicate how many Pokemon are still available to get from that type of Pokeball.\n"
                            + "Pokemon recieved are unique; you cannot get more than one of the same Pokemon."
                )
            }
        }
    }
}

@Composable
fun BulletText(text: String) {
    val lines = text.split("\n")

    val annotatedString = buildAnnotatedString {
        lines.forEachIndexed { index, line ->
            if (line.isNotBlank()) {
                withStyle(
                    style = SpanStyle(
                        color = Color.White
                    )
                ) {
                    appendInlineContent("bullet", "•")
                    append(" $line")

                    // Add extra newline if it's not the last line
                    if (index < lines.size - 1) {
                        append("\n\n")
                    }
                }
            }
        }
    }

    Text(
        text = annotatedString,
        color = Color.White,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp),
    )
}

@Composable
fun CustomHeadline(text: String) {
    Box() {
        Text(
            text = text,
            fontSize = 26.sp,
            color = Color.White,
            modifier = Modifier.padding(0.dp, 16.dp, 16.dp, 16.dp)
        )
    }
    CustomSplitter(h = 2)
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit, amount: Int, amount2: Int, basic: Boolean) {

    val newColor = if (basic) Color(106, 84, 141, 255) else Color(58, 42, 75, 255)
    Surface(
        color = newColor,
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
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CustomButtonGray(text: String, onClick: () -> Unit, amount: Int, amount2: Int, basic: Boolean) {
    Surface(
        color = Color.Gray,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(amount.dp)
            .height(amount2.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
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

@Composable
fun CustomButtonMaxWidth(text: String, onClick: () -> Unit, height: Int, basic: Boolean) {

    val newColor = if (basic) Color(106, 84, 141, 255) else Color(58, 42, 75, 255)
    Surface(
        color = newColor,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Color(255, 255, 255, 75), RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun WeatherComposable(weather: CurrentWeather?) {
    Image(
        painter = painterResource(
            id = weather?.background(weather!!.weather) ?: R.drawable.clear
        ),
        contentDescription = "Login_Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}