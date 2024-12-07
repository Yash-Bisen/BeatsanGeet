package com.example.beatsangeet.ui.screen

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.beatsangeet.R
import com.example.beatsangeet.data.model.Album
import com.example.beatsangeet.data.model.Playlist
import com.example.beatsangeet.data.model.Song
import com.example.beatsangeet.navigation.Routes
import com.example.beatsangeet.ui.appBar.MyBottomAppBar
import com.example.beatsangeet.ui.viewmodel.MusicPlayerViewModel
import com.example.beatsangeet.ui.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun S3earchScreen(navController: NavController) {
    val producerName =
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("ArtistName") ?: ""
    val image =   navController.previousBackStackEntry?.savedStateHandle?.get<String>("ArtistImage") ?: ""
    val title =   navController.previousBackStackEntry?.savedStateHandle?.get<String>("title") ?: ""
    val url =  "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"
    val mediaPlayer = remember { MediaPlayer() }
    val postViewModel : PostViewModel = hiltViewModel()
    val musicPlayerViewModel:MusicPlayerViewModel = hiltViewModel()
    val isPlaying by musicPlayerViewModel.isPlaying.observeAsState(false)

    LaunchedEffect(producerName) {
        if (producerName.isNotEmpty()) {
            postViewModel.searchArtists(producerName)
        }

    }
    val searchResults by postViewModel.artists.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Menu",
                            tint = Color.White,
                            modifier = Modifier.padding(start = 12.dp)
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
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Image(
                        painter = rememberImagePainter(data = image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(400.dp)
                            .clickable {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "ArtistName",
                                        producerName
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "title",
                                        title
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "ArtistImage",
                                        image
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "Url",
                                        url
                                    )
                                    navController.navigate(Routes.NOWPLAYINGsCREEN)
                            }
                            .padding(start = 10.dp, end = 10.dp)
,
                    )
                }
                Row(
                    modifier = Modifier .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(bottom = 28.dp)
                            .width(300.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(onClick = {
                        if (isPlaying) {
                           musicPlayerViewModel.pauseMusic()
                        } else {
                           musicPlayerViewModel.playMusic(url)
                        }
                    }) {
                        Log.i("isPlaying .....", isPlaying.toString())
                        Icon(
                            painter = if (isPlaying) rememberImagePainter(data =  R.drawable.pause) else rememberImagePainter(data = R.drawable.play),
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color.Red)
                        )
                    }
                }
            }


            item {
                Text(
                    text = "Handpicked fresh new international music from this week!",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 6.dp, bottom = 12.dp)
                )
            }

            item {
                Text(
                    text = "Songs", style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
            }

            searchResults?.songs?.data?.let { songs ->
                items(songs) { song ->
                    Row(
                        modifier = Modifier.fillMaxWidth(), // Adjusted to fill width of the screen
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SongItem(item = song, navController, mediaPlayer)
                    }
                }
            }



            item {
                Text(
                    text = "Albums", style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
            }

            item {
                LazyRow {
                    searchResults?.albums?.data?.let { albumList ->
                        items(albumList.size) { index ->
                            val album = albumList[index]
                            Log.i("url&&&&&&&&&&&&&&&&", album.url)


                            ScrollRowItems(
                                item = album,
                                onClick = {
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
                                modifier = Modifier
                                    .width((LocalConfiguration.current.screenWidthDp / 4).dp)
                                    .height(120.dp)  // Fixed height for each card
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Playlists", style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    searchResults?.playlists?.data?.let { albumList ->
                        items(albumList.size) { index ->
                            val album = albumList[index]

                            PlaylistFunc(
                                item = album,
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
            }
        }
    }
}

@Composable
fun ScrollRowItems(item: Album, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(
                4.dp
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
                painter = rememberImagePainter(data = item.image)  ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxSize()
            )

            Text(
                modifier = Modifier.padding(end = 6.dp, top = 10.dp, bottom = 15.dp),
                text = item.title, style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.Gray,
                ),
                maxLines = 4,
                minLines = 4
            )
        }
    }
}


@Composable
fun PlaylistFunc(item: Playlist, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val title = item.title
    Box(
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxSize()
                    .clip(CircleShape)
            )

            Text(
                modifier = Modifier.padding(end = 6.dp, top = 10.dp, bottom = 15.dp),
                text = title,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = Color.Gray,
                )
            )
        }
    }
}


@Composable
fun SongItem(item: Song, navController: NavController, mediaPlayer: MediaPlayer?) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(60.dp)
            .padding(8.dp)
            .clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "videoUrl",
                    "\"https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4\""
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        ) {
            Image(
                painter = rememberImagePainter(data = item.image),
                contentDescription = "Launcher Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(200.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 15.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
                Text(
                    text = item.more_info.singers,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,

                        ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) {
            IconButton(onClick = {
                if (isFavorite) {
                    //  mediaPlayer?.pause()
                } else {
                    //mediaPlayer?.start()
                }
                isFavorite = !isFavorite
            }) {
                Icon(
                    painter = if (isFavorite) rememberImagePainter(data = R.drawable.favorite) else rememberImagePainter(
                        data = R.drawable.notfavorite
                    ),
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) {
            IconButton(onClick = {
                showBottomSheet = true
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "MoreVert",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        if (showBottomSheet) {
            MusicPlayerBottomSheet(
                onClose = { showBottomSheet = false },
                item = item
            )
        }
    }
}

