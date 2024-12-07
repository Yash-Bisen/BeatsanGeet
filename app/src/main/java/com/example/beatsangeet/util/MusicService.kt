package com.example.beatsangeet.util

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPrepared = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        val url = intent?.getStringExtra("URL")

        when (action) {
            "PLAY" -> playMusic(url)
            "PAUSE" -> pauseMusic()
            "STOP" -> stopMusic()
        }

        return START_STICKY
    }

    private fun playMusic(url: String?) {
        if (url == null) {
            Log.i("MusicService", "URL is null")
            return
        }

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener {
                    isPrepared = true
                    start() // Start only after preparation
                    Log.i("MusicService", "Music started")
                }
                setOnErrorListener { mp, what, extra ->
                    Log.e("MusicService", "Error: $what, $extra")
                    resetMediaPlayer() // Reset on error
                    true
                }
                prepareAsync() // Async preparation
            }
        } else if (isPrepared && !mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
            Log.i("MusicService", "Resumed playing music")
        }
    }

    private fun pauseMusic() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            Log.i("MusicService", "Music paused")
        }
    }

    private fun stopMusic() {
        mediaPlayer?.stop()
        resetMediaPlayer()
        Log.i("MusicService", "Music stopped")
    }

    private fun resetMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        isPrepared = false
    }
}
