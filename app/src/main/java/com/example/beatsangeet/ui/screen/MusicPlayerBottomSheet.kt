package com.example.beatsangeet.ui.screen

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.beatsangeet.R
import com.example.beatsangeet.data.model.Song

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerBottomSheet(
    onClose :()-> Unit,
    item : Song
) {
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = rememberModalBottomSheetState(),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = rememberImagePainter(data = item.image),
                        contentDescription = "",
                        modifier = Modifier
                            .size(65.dp)
                            .padding(start = 10.dp, end = 12.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            ),
                        )

                        Text(
                            text = item.more_info.singers,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Light,

                                ),
                            maxLines = 2
                        )

                    }
                }
                Divider(
                    color = Color.LightGray, // You can change the color as needed
                    thickness = 1.dp, // You can adjust the thickness
                    modifier = Modifier.padding(vertical = 2.dp) // Add padding if needed
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp, top = 6.dp)
                ) {
                    Icon(painter = rememberImagePainter(data = R.drawable.favorite),
                        contentDescription ="Add to Liked Songs",
                        modifier = Modifier
                            .padding(6.dp)
                            .size(18.dp)
                    )
                    Text(
                        text = "Add to Liked Songs",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,

                            )
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp, top = 6.dp)
                ) {
                    Icon(painter = rememberImagePainter(data = R.drawable.add),
                        contentDescription ="Add to Playlist",
                        modifier = Modifier
                            .padding(6.dp)
                            .size(18.dp)
                    )
                    Text(
                        text = " Add to PlayList",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,

                            )
                    )

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp, top = 6.dp)
                ) {
                    Icon(painter = rememberImagePainter(data = R.drawable.remove),
                        contentDescription ="Hide Song",
                        modifier = Modifier
                            .padding(6.dp)
                            .size(18.dp)
                    )
                    Text(
                        text = " Hide this Song",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            )
                    )
                }
            }
        },
    )
}
