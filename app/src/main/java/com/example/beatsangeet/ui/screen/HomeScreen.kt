package com.example.beatsangeet.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.beatsangeet.R
import com.example.beatsangeet.data.model.Album
import com.example.beatsangeet.data.model.FeaturedPlaylist
import com.example.beatsangeet.data.model.NewAlbum
import com.example.beatsangeet.navigation.Routes
import com.example.beatsangeet.ui.appBar.MyBottomAppBar
import com.example.beatsangeet.ui.theme.Grey10
import com.example.beatsangeet.ui.viewmodel.PostViewModel
import kotlin.random.Random


val VIDEO_URL : List<String> = listOf(
     "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
     "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
     "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
     "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
     "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
     )

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController : NavController) {
    val ite = listOf(
        "Item 1",
        "Item 2",
        "Item 3",
        "Item 4",

    )
    val postViewModel : PostViewModel = hiltViewModel()
    val albums by postViewModel.albums.collectAsState()
    val fearturedPlayList by postViewModel.fearturedPlayList.collectAsState()
    val albumsKk by postViewModel.albumsKk.collectAsState()
    val albumsArijit by postViewModel.albumsArijit.collectAsState()



    Scaffold(
            topBar = {
                TopAppBar({ Text(text = "Home") })
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
                items(albums.drop(19).take(8).chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp), // Optional padding around the entire row
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (currentSingerItem in rowItems) {
                            TwoColumnItem(
                                item = currentSingerItem,
                                modifier = Modifier
                                    .weight(0.5f) // Make each item take equal space
                                    .padding(horizontal = 4.dp), // Padding between items
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "ArtistName",
                                        currentSingerItem.Artist.music.first().name
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "title",
                                        currentSingerItem.title
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "ArtistImage",
                                        currentSingerItem.image
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "SongUrl",
                                        currentSingerItem.albumid
                                    )
                                    navController.navigate(Routes.SEARCHSCREEN3)
                                }
                            )
                        }
                    }
                }


                item {
                    Text(
                        text = "Jump back in", style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                        items(albums) { album ->
                             ScrollRowItem(
                                item = album,
                                 onClick = {
                                     navController.currentBackStackEntry?.savedStateHandle?.set(
                                         "ArtistName",
                                         album.Artist.music.first().name
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

                item {
                    Text(
                        text = "Featured Playlists", style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                        items(fearturedPlayList.drop(1)) { item ->
                            Log.i("&&&&&&&&&&&&&&&&&&&&&&&&&",item.toString())
                            FeaturedPlaylist(
                                item = item,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "ArtistName",
                                        item.firstname
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "title",
                                        item.listname ?: "Unknown Title"
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "ArtistImage",
                                        item.image
                                    )
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "SongUrl",
                                        item.perma_url
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

                item {
                    Text(
                        text = "Sanam Puri Retro hits Specials", style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                        items(albumsKk) { album ->
                            Log.i("kkspecial-----------",album.url)
                                   ArtistSpecials(
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
                                           navController.currentBackStackEntry?.savedStateHandle?.set(
                                               "SongUrl",
                                               album.url
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

                item {
                    Text(
                        text = "Arijit Specials", style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                        items(albumsArijit) { album ->
                            Log.i("Arijitspecial-----------",album.url)
                            ArtistSpecials(
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
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "SongUrl",
                                        album.url
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

                item {
                    Text(
                        text = "Recommanded Stations", style = TextStyle(
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                    )
                }

                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(ite.size) { item ->
                            RecommandedStationsList(
                                item = item.toString(),
                                onClick = {
                                    navController.navigate(Routes.SEARCHSCREEN3)
                                },
                                modifier = Modifier
                                    .width((LocalConfiguration.current.screenWidthDp / 4).dp)
                                    .height(120.dp),
                                getRandomColor()// Fixed height for each card
                            )
                        }
                    }
                }

                item{
                    Spacer(modifier = Modifier.size(50.dp))
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Grey10,
                        modifier = Modifier
                            .padding(
                                top = 4.dp,
                                end = 10.dp,
                                bottom = 4.dp
                            )
                            .fillMaxWidth()
                            .height(200.dp)

                    ) {
                        Log.i("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", albumsKk.size.toString())
                        if(!albumsKk.isEmpty())
                            ExoVideoPlayer(files = albumsKk)
                    }
                }
            }
        }

}

@Composable
fun TwoColumnItem(
    item: NewAlbum,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // Default parameter value
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Grey10 ,
        modifier = modifier
            .padding(
                top = 4.dp,
                end = 10.dp,
                bottom = 4.dp
            )
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .padding(horizontal = 8.dp), // Optional: Padding inside the Row
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .weight(0.3f)
            )

            Text(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 8.dp, end = 6.dp, top = 10.dp, bottom = 15.dp),
                text = item.Artist.music.first().name,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = Color.White,
                ),
                maxLines = 2
            )
        }
    }
}


@Composable
fun ScrollRowItem(item: NewAlbum, modifier: Modifier = Modifier,  onClick: () -> Unit) {
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
                maxLines = 3,
                minLines = 3
            )
        }

    }
}


@Composable
fun FeaturedPlaylist(item: FeaturedPlaylist, modifier: Modifier = Modifier,  onClick: () -> Unit) {
    val title = item.listname ?: "Unknown Title"
    Log.i("FeatueredPlaylist***********************************", item.toString())
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
                ),
                maxLines = 3,
                minLines = 3
            )
        }

    }
}


@Composable
fun ArtistSpecials(item:Album , modifier: Modifier = Modifier,  onClick: () -> Unit) {
    Log.i("KK*************************", item.toString())
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
                painter = rememberImagePainter(data = item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxSize()
                    .clip(CircleShape)
            )

            Text(
                modifier = Modifier.padding(end = 6.dp, top = 10.dp, bottom = 15.dp),
                text = item.title,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = Color.Gray,
                ),
                maxLines = 3,
                minLines = 3
            )
        }

    }
}

@Composable
fun RecommandedStationsList(item: String,onClick: () -> Unit, modifier: Modifier = Modifier, randomColor: Color) {
    Box(modifier = Modifier
        .wrapContentWidth()
        .clipToBounds()
        .background(color = randomColor)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier  = Modifier
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Icon(painter = painterResource(id = R.drawable.baseline_music_off_24),
                    contentDescription = "",
                    modifier = Modifier.height(17.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(70.dp))
                Text(text = "RADIO", style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = Color.Black,
                )
                )
            }
            Box(
                modifier = Modifier
                    .height(72.dp)
                    .clipToBounds(),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier
                        .clipToBounds()
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.shaan),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp),
//                            .offset(x=10.dp),
                        contentScale = ContentScale.Crop,

                        )

                    Spacer(modifier = Modifier.width(55.dp))

                    Image(
                        painter = painterResource(id = R.drawable.uditnarayan),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .size(50.dp),
//                            .offset(x= -10.dp),
                        contentScale = ContentScale.Crop,

                        )
                }
                Box(modifier = modifier
                    .size(71.dp)
                    .padding(horizontal = 10.dp)
                    .clip(CircleShape)
                    .background(color = randomColor) ,
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sonunigam),
                        contentDescription = "",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(
                                CircleShape
                            )
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            Text(text = "Sonu Nigam",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle( fontSize = MaterialTheme.typography.titleSmall.fontSize, fontWeight = Bold, color = Color.Black)
            )
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoVideoPlayer(files: List<Album>) {
    var currentIndex by remember { mutableStateOf(0) }

    val context = LocalContext.current
    val exoPlayer = remember {
        getSimpleExoPlayer(context, files[currentIndex])
    }

    AndroidView(

        factory = { context1 ->
            PlayerView(context1).apply {
                player = exoPlayer
                useController = false
                keepScreenOn = true
                player?.playWhenReady = true
            }
        },
    )

    Column (
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = rememberImagePainter(data =files[currentIndex].image)  ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(15.dp)),

            )

            Column(

                modifier = Modifier.width(250.dp)
            ) {
                Text(text = files[currentIndex].title, style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize, color = Color.White))
                Text(text = files[currentIndex].music, style = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize, color = Color.Gray))
            }

            Icon(imageVector = Icons.Default.AddCircle, contentDescription = " Add")
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = " backword",
                modifier = Modifier
                    .clickable {
                        if (currentIndex > 0) {
                            currentIndex -= 1
                            exoPlayer.setMediaItem(MediaItem.fromUri(files[currentIndex].url))
                            exoPlayer.prepare()
                            exoPlayer.playWhenReady = true
                        }
                    }
                    .size(30.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.LightGray.copy(alpha = 0.5f),
                            radius = 15.dp.toPx()
                        )
                    },
                tint = Color.Black
            )
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = " forward",
                modifier = Modifier
                    .clickable {

                        if (currentIndex < files.size - 1) {
                            currentIndex += 1
                            exoPlayer.setMediaItem(MediaItem.fromUri(files[currentIndex].url))
                            exoPlayer.prepare()
                            exoPlayer.playWhenReady = true
                        }
                    }
                    .size(30.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.LightGray.copy(alpha = 0.5f),
                            radius = 15.dp.toPx()
                        )
                    },
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        ) {

            Text(text =files[currentIndex].music, style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize, color = Color.White))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.Black.copy(alpha = 0.6f)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.height(30.dp)) {
                        Icon(
                            painter = painterResource( R.drawable.baseline_music_off_24),
                            contentDescription = "Morevert",
                            tint = Color.White,

                            )
                    }

                    Text(text = "preview playlist", style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize, color = Color.Gray.copy(alpha=0.7f)))
                }

                IconButton(onClick = { /*TODO*/ },
                    modifier = Modifier.height(30.dp)) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Morevert",
                        tint = Color.White,

                        )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f))
                        .clip(CircleShape)
                        .size(18.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Morevert",
                        tint = Color.Black
                    )
                }

            }
        }

    }
}

fun getRandomColor(): Color {
    return Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
        alpha = 1.0f
    )
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
private fun getSimpleExoPlayer(context: Context, file: Album): SimpleExoPlayer {
    return SimpleExoPlayer.Builder(context).build().apply {
        val dataSourceFactory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        //local video
        val localVideoItem = MediaItem.fromUri(file.url)
        val localVideoSource = ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(localVideoItem)
        this.addMediaSource(localVideoSource)

        // streaming from internet
        val internetVideoItem = MediaItem.fromUri(file.url)
        val internetVideoSource = ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(internetVideoItem)
        this.addMediaSource(internetVideoSource)
        // init
        this.prepare()
    }
}
