package com.example.beatsangeet.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.beatsangeet.R
import com.example.beatsangeet.navigation.Routes
import com.example.beatsangeet.ui.appBar.MyBottomAppBar
import com.example.beatsangeet.ui.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun S2earchScreen(navController: NavController) {
    val titleText =
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("statusValue") ?: ""

    val postViewModel : PostViewModel = hiltViewModel()

    val genre by postViewModel.genre.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = titleText) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.SEARCH)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Menu",
                            tint = Color.White, // Optional: You can customize the tint/color of the icon
                            modifier = Modifier.padding(start = 12.dp) // Optional: Adjust padding as needed
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),

                )
        },
        bottomBar = {
            MyBottomAppBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 10.dp,
                start = 12.dp,
                end = 12.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Text(
                    text = "Discover New Music",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(start = 12.dp, top = 18.dp, bottom = 18.dp)
                )
            }


            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(ite.size) { item ->
                        ItemMusic(
                            item = item.toString(),
                            onClick = {

                                navController.navigate(Routes.SEARCHSCREEN3)

                            },
                            modifier = Modifier
                                .width((LocalConfiguration.current.screenWidthDp / 4).dp)
                                .height(120.dp)  // Fixed height for each card
                        )
                    }
                }
            }
            item {
                Text(
                    text = "PlayList from out Editors",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
                )
            }


            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(ite.size) { item ->
                        ItemMusic(
                            item = item.toString(),
                            onClick = {

                            },
                            modifier = Modifier
                                .width((LocalConfiguration.current.screenWidthDp / 4).dp)
                                .height(120.dp)  // Fixed height for each card
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Browse all",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
                )
            }

           genre .drop(4) .chunked(2).forEach { rowItems ->
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowItems.forEach { item ->
                            BrowserAllItem(item = item,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "statusValue",
                                        it
                                    )
                                    navController.navigate(Routes.SEARCHSCREEN2)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemMusic(item: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(
                top = 4.dp,
                bottom = 4.dp
            )
            .width(130.dp)
    ) {
        Column(
            modifier = Modifier.clickable {
                onClick()
            },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.anni),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxSize()
            )
            Text(
                modifier = Modifier.padding(end = 6.dp, top = 10.dp, bottom = 15.dp),
                text = "karan Aujla, Diljit Dosanjh, Sidhu Moosewala", style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.Gray,
                )
            )
        }
    }
}

val ite = listOf(
    "Item 1",
    "Item 2",
    "Item 3",
    "Item 4",
    "Item 5",
    )


