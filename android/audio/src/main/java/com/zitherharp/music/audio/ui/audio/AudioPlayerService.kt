package com.zitherharp.music.audio.ui.audio

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

class AudioPlayerService(): Service() {
    private lateinit var audioPlayer: YouTubePlayer
    private lateinit var audioId: String
    private var audioPosition: Float = 0f

    constructor(youTubePlayer: YouTubePlayer): this() {
        audioPlayer = youTubePlayer
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            audioId = it.getStringExtra("audioId").toString()
            audioPosition = it.getFloatExtra("audioPosition", 0f)
            audioPlayer.loadVideo(audioId, audioPosition)
            audioPlayer.play()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        audioPlayer.pause()
        return super.onUnbind(intent)
    }
}