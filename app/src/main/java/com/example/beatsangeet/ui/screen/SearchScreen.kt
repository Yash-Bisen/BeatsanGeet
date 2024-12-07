package com.example.beatsangeet.ui.screen

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.beatsangeet.data.model.FeaturedPlaylist
import com.example.beatsangeet.data.model.Genre
import com.example.beatsangeet.navigation.Routes
import com.example.beatsangeet.ui.appBar.MyBottomAppBar
import com.example.beatsangeet.ui.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SearchScreen(navController: NavController) {

    val postViewModel : PostViewModel = hiltViewModel()
    val genre by postViewModel.genre.collectAsState()

    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val searchResults by postViewModel.artists.observeAsState()
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    val playlist by postViewModel.fearturedPlayList.collectAsState()

    @Composable
    fun CustomTextField(
        query: String,
        onValueChange: (String) -> Unit,
        onSearchTriggered: () -> Unit
    ) {
        TextField(
            value = query,
            onValueChange = { newQuery ->
                onValueChange(newQuery)
                if (newQuery.isEmpty()) {
                    onSearchTriggered()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            trailingIcon = {
                IconButton(onClick = {
                    onSearchTriggered() // Trigger data fetching when the search icon is clicked
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchTriggered() // Trigger search when the Enter key is pressed
                }
            ),
            maxLines = 1
        )
    }

    Scaffold(
        topBar = {
            TopAppBar({ Text(text = "Search") })
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
            modifier = Modifier.fillMaxWidth()
        ) {
            item{
                CustomTextField(
                    query = query,
                    onValueChange = { newQuery ->
                        query = newQuery
//                        postViewModel.fetchAutoComplete(newQuery)
                    },
                    onSearchTriggered = {
                        if (query.isNotEmpty()) {
                            postViewModel.searchArtists(query)
                        } else {
                            postViewModel.clearResults()
                        }
                    }

                )

            }
            searchResults?.albums?.data?.let { albumList ->
                        items(albumList.size) { index ->
                            val album = albumList[index]
                        Log.i("url&&&&&&&&&&&&&&&&", album.url)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "ArtistName",
                                            album.music
                                        )
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "title",
                                            album.title
                                        )
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "ArtistImage",
                                            album.image
                                        )
                                        navController.navigate(Routes.SEARCHSCREEN3)
                                    },
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = rememberImagePainter(data = album.image),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(end = 15.dp)
                                        .clip(RoundedCornerShape(15.dp)),

                                    )

                                Column {
                                    Text(
                                        text = album.title,
                                        style = TextStyle(
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            color = Color.White
                                        )
                                    )
                                    Text(
                                        text = album.music,
                                        style = TextStyle(
                                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                            color = Color.Gray
                                        )
                                    )
                                }

                            }
                        }
                    }

            searchResults?.playlists?.data?.let { playlist ->
                items(playlist.size) { index ->
                    val playitem = playlist[index]

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clickable {
                                mediaPlayer?.stop()
                                mediaPlayer = MediaPlayer().apply {
                                    setDataSource(playitem.url) // Set the data source to the music URL
                                    prepare()
                                    start() // Start playing the music
                                }
                            },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(data = playitem.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 15.dp)
                                .clip(RoundedCornerShape(15.dp)),

                            )

                        Column {
                            Text(
                                text = playitem.title,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = playitem.description,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    color = Color.Gray
                                )
                            )
                        }

                    }
                }
            }



            item {
                Text(
                    text = "Explore Your Genres",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
                )
            }

            item {
                // Display 3 ImageTextView items in parallel
                LazyRow {
                       items(genre) { item ->
                        ImageTextView(
                            item,
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "ArtistName",
                                    item.about
                                )
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "title",
                                    item.title
                                )
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "ArtistImage",
                                    item.image
                                )
                                navController.navigate(Routes.SEARCHSCREEN3)
                            },
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


            genre.chunked(2).forEach { rowItems ->
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        rowItems.forEach { item ->
                            BrowserAllItem(item = item,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("statusValue",it)
                                    navController.navigate(Routes.SEARCHSCREEN2)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

}

@Composable
fun BrowserAllItem(item:Genre, onClick: (text :String) -> Unit) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .height(110.dp)
            .padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 4.dp)
            .background(getRandomColor())
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                onClick = {
                    onClick(item.tags)
                }
            )
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        )

        // Adding an Image within a BoxWithConstraints for proper positioning and cropping
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 10.dp)  // Adjust this padding as needed
                .clip(RoundedCornerShape(8.dp))  // Ensure the content is clipped
        ) {
            Image(
                painter = rememberImagePainter(data = item.image),
                contentDescription = "Launcher Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .rotate(10f)
                    .align(Alignment.TopEnd)
                    .offset(
                        x = 18.dp,
                        y = 14.dp
                    )  // Adjust the offset to move the image further right
                    .height(90.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}


@Composable
fun ImageTextView(item: Genre, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(180.dp)
            .padding(6.dp)
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = rememberImagePainter(data = item.image),
            contentDescription = "Launcher Background",
            contentScale = ContentScale.Crop, // Use Crop to fill the box size properly
            modifier = Modifier.fillMaxSize() // Use fillMaxSize to fill the entire box
        )
        Text(
            text = "#"+item.tags,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(navController = rememberNavController())
}