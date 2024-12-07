package com.example.beatsangeet.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.beatsangeet.R
import com.example.beatsangeet.ui.appBar.MyBottomAppBar
import com.example.beatsangeet.data.model.Artist
import com.example.beatsangeet.data.model.DataProvider
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar({ Text(text = "Library") })
        },
        bottomBar = {
            MyBottomAppBar(navController)
        }
    ){ innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom =innerPadding.calculateBottomPadding() +10.dp,
                start = 12.dp,
                end = 12.dp
            ),modifier = Modifier
                .fillMaxWidth()
        ){
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.updown),
                                contentDescription = ", ",
                                tint = Color.White,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "Recent",
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = ", ",
                        tint = Color.White,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            val users = DataProvider.puppyList
            if (users != null) {
                items(items = users ) { user ->
                    ArtistList(user = user)
                }
            }

        }
    }
}

@Composable
fun ArtistList(user: Artist){
    Row {
        val imagePainter = if (user.puppyImageId != null) {
            rememberAsyncImagePainter(model = user.puppyImageId)
        } else {
            painterResource(id = R.drawable.sonunigam)
        }

        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )


        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {

            Text(text = user.name, style = typography.titleMedium)
            Text(text = "Artist", style = typography.bodyMedium)

        }
    }
}