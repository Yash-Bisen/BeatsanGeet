package com.example.beatsangeet.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beatsangeet.data.domain.Streamable
import com.example.beatsangeet.navigation.Routes
import com.example.beatsangeet.ui.screen.HomeScreen
import com.example.beatsangeet.ui.screen.LibraryScreen
import com.example.beatsangeet.ui.screen.NowPlayingScreen
import com.example.beatsangeet.ui.screen.PremiumScreen
import com.example.beatsangeet.ui.screen.S2earchScreen
import com.example.beatsangeet.ui.screen.S3earchScreen
import com.example.beatsangeet.ui.screen.SearchScreen
import com.example.beatsangeet.ui.theme.BeatsanGeetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            BeatsanGeetTheme {

                NavHost(navController = navController, startDestination = Routes.HOME, builder = {
                    composable(Routes.HOME){
                        HomeScreen(navController)
                    }

                    composable(Routes.SEARCH){
                        SearchScreen(navController)
                    }
                    composable(Routes.LIBRARY){
                        LibraryScreen(navController)
                    }

                    composable(Routes.PREMIUM){
                        PremiumScreen(navController = navController)

                    }
                    composable(Routes.SEARCHSCREEN2){
                        S2earchScreen(navController)
                    }

                    composable(Routes.SEARCHSCREEN3,){
                        S3earchScreen(navController)
                    }

                    composable(Routes.NOWPLAYINGsCREEN){
                        NowPlayingScreen(navController)

                    }
                })
            }
        }
    }
}
