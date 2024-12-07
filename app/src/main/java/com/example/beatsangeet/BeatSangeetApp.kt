package com.example.beatsangeet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BeatSangeetApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Application-level setup
    }
}