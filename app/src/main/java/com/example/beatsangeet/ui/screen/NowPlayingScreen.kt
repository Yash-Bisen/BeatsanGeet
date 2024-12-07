package com.example.beatsangeet.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.beatsangeet.R
import com.example.beatsangeet.data.domain.StreamInfo
import com.example.beatsangeet.data.domain.Streamable
import com.example.beatsangeet.di.component.AsyncImageWithPlaceholder
import com.example.beatsangeet.ui.viewmodel.MusicPlayerViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.http.Url

// collecting the flow within the composable scopes the collector to the composable.
// This ensures that the collection of flow is stopped as soon this composable
// is removed from composition. Therefore, this composables use parameters of type
// Flow.
@Composable
fun NowPlayingScreen(
    navController : NavController,
    streamable: Streamable =Streamable(
        streamInfo = StreamInfo(
            streamUrl = "https://example.com/default.mp3", // Provide a default stream URL
            imageUrl = "https://example.com/default.jpg",  // Provide a default image URL
            title = "Default Title",                      // Provide a default title
            subtitle = "Default Subtitle"                 // Provide a default subtitle
        )
    ), // Replace with a proper default instance if available
    playbackProgressFlow: Flow<Float> = flowOf(0f), // Default flow with 0 progress
    timeElapsedStringFlow: Flow<String> = flowOf("00:00"), // Default flow with "00:00" as the elapsed time
    playbackDurationRange: ClosedFloatingPointRange<Float> = 0f..1f, // Default range from 0 to 1
    isPlaybackPaused: Boolean = true, // Default to playback being paused
    totalDurationOfCurrentTrackProvider: () -> String = { "00:00" }, // Default total duration to "00:00"
    onCloseButtonClicked: () -> Unit = {}, // Default empty lambda for close button
    onShuffleButtonClicked: () -> Unit = {}, // Default empty lambda for shuffle button
    onSkipPreviousButtonClicked: () -> Unit = {}, // Default empty lambda for skip previous button
    onPlayButtonClicked: () -> Unit = {}, // Default empty lambda for play button
    onPauseButtonClicked: () -> Unit = {}, // Default empty lambda for pause button
    onSkipNextButtonClicked: () -> Unit = {}, // Default empty lambda for skip next button
    onRepeatButtonClicked: () -> Unit = {}
) {
    val musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()

    var isImageLoadingPlaceholderVisible by remember { mutableStateOf(true) }
    val producerName =
        navController.previousBackStackEntry?.savedStateHandle?.get<String>("ArtistName") ?: ""
    val image =   navController.previousBackStackEntry?.savedStateHandle?.get<String>("ArtistImage") ?: ""
    val title =   navController.previousBackStackEntry?.savedStateHandle?.get<String>("title") ?: ""
    val url =   navController.previousBackStackEntry?.savedStateHandle?.get<String>("Url") ?: ""

    // All built-in compose layouts don't use a surface to display the content.
    // This means, if there is a list of clickable tracks displayed behind
    // the layout, then it will be possible to click them even if they are
    // not visible. To prevent such a behavior, surround the NowPlayingScreen
    // content with a surface.
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Header(
                modifier = Modifier.fillMaxWidth(),
                onCloseButtonClicked = onCloseButtonClicked,
                onTrailingButtonClick = {}
            )
            Spacer(modifier = Modifier.size(64.dp))
            AsyncImageWithPlaceholder(
                modifier = Modifier
                    .size(330.dp)
                    .align(Alignment.CenterHorizontally),
                model = image,
                contentDescription = null,
                onImageLoadingFinished = { isImageLoadingPlaceholderVisible = false },
                isLoadingPlaceholderVisible = isImageLoadingPlaceholderVisible,
                onImageLoading = { isImageLoadingPlaceholderVisible = true }
            )
            Spacer(modifier = Modifier.size(64.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = producerName,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(8.dp))
            Box {
                ProgressSliderWithTimeText(modifier = Modifier.fillMaxWidth(),
                    currentTimeElapsedStringFlow = timeElapsedStringFlow,
                    totalDurationOfTrack = totalDurationOfCurrentTrackProvider(),
                    currentPlaybackProgressFlow = playbackProgressFlow,
                    playbackDurationRange = playbackDurationRange,
                    onSliderValueChange = {})
            }
            PlaybackControls(
                modifier = Modifier.fillMaxWidth(),
                isPlayIconVisible = isPlaybackPaused,
                onSkipPreviousButtonClicked = onSkipPreviousButtonClicked,
                onSkipNextButtonClicked = onSkipNextButtonClicked,
                onRepeatButtonClicked = onRepeatButtonClicked,
                onShuffleButtonClicked = onShuffleButtonClicked,
                url = url
            )
            Footer(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                onAvailableDevicesButtonClicked = {},
                onShareButtonClicked = {}
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    onCloseButtonClicked: () -> Unit,
    onTrailingButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val expandMoreIcon = painterResource(R.drawable.ic_expand_more_24)
        val moreHorizIcon = painterResource(id = R.drawable.ic_more_horiz_24)
        IconButton(modifier = Modifier.offset(x = (-16).dp), // accommodate for increased size of icon because of touch target sizing
            onClick = onCloseButtonClicked,
            content = { Icon(painter = expandMoreIcon, contentDescription = null) })
        Text(
            text = "Now playing",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        IconButton(modifier = Modifier.offset(x = (16).dp), // accommodate for increased size of icon because of touch target sizing
            onClick = onTrailingButtonClick,
            content = { Icon(painter = moreHorizIcon, contentDescription = null) })
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    onShareButtonClicked: () -> Unit,
    onAvailableDevicesButtonClicked: () -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val availableDevicesIcon = painterResource(id = R.drawable.ic_available_devices)
        IconButton(onClick = onAvailableDevicesButtonClicked,
            content = { Icon(painter = availableDevicesIcon, contentDescription = null) })
        IconButton(onClick = onShareButtonClicked,
            content = { Icon(imageVector = Icons.Filled.Share, contentDescription = null) })
    }
}

@Composable
private fun PlaybackControls(
    modifier: Modifier = Modifier,
    isPlayIconVisible: Boolean,
    onSkipPreviousButtonClicked: () -> Unit,
    onShuffleButtonClicked: () -> Unit,
    onSkipNextButtonClicked: () -> Unit,
    onRepeatButtonClicked: () -> Unit,
    url:String
) {
    val musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()
    val isPlaying by musicPlayerViewModel.isPlaying.observeAsState()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onShuffleButtonClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_round_shuffle_24), contentDescription = null
            )
        }
        IconButton(onClick = onSkipPreviousButtonClicked) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.ic_skip_previous_24),
                contentDescription = null
            )
        }
        IconButton(onClick = {
            if (isPlaying == true)
                musicPlayerViewModel.pauseMusic()
            else
               musicPlayerViewModel.playMusic(url)
        }) {
            Icon(
                modifier = Modifier.size(72.dp),
                painter = if (isPlaying == false) painterResource(R.drawable.ic_play_circle_filled_24)
                else painterResource(R.drawable.ic_pause_circle_filled_24),
                contentDescription = null
            )
        }
        IconButton(onClick = onSkipNextButtonClicked) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.ic_skip_next_24),
                contentDescription = null
            )
        }
        IconButton(onClick = onRepeatButtonClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_round_repeat_24), contentDescription = null
            )
        }
    }
}

@Composable
private fun ProgressSliderWithTimeText(
    modifier: Modifier = Modifier,
    currentTimeElapsedStringFlow: Flow<String>,
    currentPlaybackProgressFlow: Flow<Float>,
    totalDurationOfTrack: String,
    playbackDurationRange: ClosedFloatingPointRange<Float>,
    onSliderValueChange: (Float) -> Unit
) {
    val currentProgress by currentPlaybackProgressFlow.collectAsState(initial = 0f)
    val timeElapsedString by currentTimeElapsedStringFlow.collectAsState(initial = "00:00")
    Column(modifier = modifier) {
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = currentProgress,
            valueRange = playbackDurationRange,
            colors = SliderDefaults.colors(
                thumbColor = Color.White, activeTrackColor = Color.White
            ),
            onValueChange = onSliderValueChange
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = timeElapsedString, style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = totalDurationOfTrack, style = MaterialTheme.typography.titleSmall
            )
        }
    }
}
