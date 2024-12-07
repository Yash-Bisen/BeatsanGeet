package com.example.beatsangeet.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beatsangeet.util.MusicService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val application: Application
) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    fun playMusic(url: String) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = "PLAY"
            putExtra("URL", url)
        }
        context.startService(intent)
        Log.i("start service called",context.startService(intent).toString() )
        _isPlaying.value = true
    }

    fun pauseMusic() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = "PAUSE"
        }
        context.startService(intent)
        _isPlaying.value = false
    }
}
