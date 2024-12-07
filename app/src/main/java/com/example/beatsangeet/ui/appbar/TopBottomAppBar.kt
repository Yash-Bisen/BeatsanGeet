package com.example.beatsangeet.ui.appBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.beatsangeet.R
import com.example.beatsangeet.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(title: String,navigate: NavController, showBackButton : Boolean = false , picture:ImageVector, onBackClick : (() -> Unit)?=null){
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(title = { Text(text = title) }, navigationIcon = {
        if(showBackButton){
            IconButton(onClick = { onBackClick?.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    }, actions = {
        IconButton(onClick = { expanded = true }) {
            Icon(imageVector = picture, contentDescription = "Back")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
//            if (picture == Icons.Default.MoreVert) {
//                DropdownMenuItem(
//                    text = { Text("unfavourite All") },
//                    onClick = { mainViewModel.updateFavouriteAll(false)
//                        expanded = false
//                        navigate.navigate(Routes.favourite)}
//                )
//            }else{
//                DropdownMenuItem(
//                    text = { Text("Settings") },
//                    onClick = {  expanded = false }
//                )
//                DropdownMenuItem(
//                    text = { Text("logout") },
//                    onClick = { expanded = false
//                        sessionManagerViewModel.logoutUser()
//                        navigate.navigate(Routes.login)
//                    },
//
//                    )
//            }
        }
    })
}


@Composable
fun MyBottomAppBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val gradientBrush = remember {
        Brush.verticalGradient(
            colorStops = arrayOf(
                0.0f to Color.Black,
                0.3f to Color.Black.copy(alpha = 0.9f),
                0.5f to Color.Black.copy(alpha = 0.8f),
                0.7f to Color.Black.copy(alpha = 0.6f),
                0.9f to Color.Black.copy(alpha = 0.2f),
                1f to Color.Transparent
            ),
            startY = Float.POSITIVE_INFINITY,
            endY = 0.0f
        )
    }

    Surface(
        modifier = Modifier
            .background(gradientBrush)
            .height(80.dp)
            ,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                selected = currentDestination?.route == Routes.HOME,
                onClick = { navController.navigate(Routes.HOME) },
            )
            NavigationItem(
                icon = Icons.Default.Search,
                label = "Search",
                selected = currentDestination?.route == Routes.SEARCH,
                onClick = {  navController.navigate(Routes.SEARCH)}
            )
            NavigationItem(
                iconResId = R.drawable.library,
                label = "Library",
                selected = currentDestination?.route == Routes.LIBRARY,
                onClick = {  navController.navigate(Routes.LIBRARY)}
            )
            NavigationItem(
                iconResId = R.drawable.premium,
                label = "Premium",
                selected = currentDestination?.route == Routes.PREMIUM,
                onClick = { navController.navigate(Routes.PREMIUM) }
            )
        }
    }
}

@Composable
fun NavigationItem(
    icon: ImageVector? = null,
    iconResId: Int? = null,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick,
        modifier = Modifier.size(60.dp)
            .padding(top = 5.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (selected) Color.White else Color.Gray
                )
            } else if (iconResId != null) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = label,
                    tint = if (selected) Color.White else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = label,
                style = TextStyle(
                    color = if (selected) Color.White else Color.Gray,
                    fontSize = 10.sp
                ),
            )
        }
    }
}
