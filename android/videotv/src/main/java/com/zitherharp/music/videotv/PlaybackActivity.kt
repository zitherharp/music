package com.zitherharp.music.videotv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.zitherharp.music.model.Video

class PlaybackActivity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_launcher)
//        supportFragmentManager.beginTransaction()
//            .replace(android.R.id.content, VideoPlayerFragment())
//            .commitNow()
    }

    class VideoPlayerFragment: Fragment() {
        private lateinit var videoPlayerView: YouTubePlayerView

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(
                R.layout.activity_launcher, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val video = activity?.intent?.getSerializableExtra(
                PlaybackActivity::class.java.name) as Video
            videoPlayerView = view.findViewById(R.id.video_player)
            videoPlayerView.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(video.id, 0F)
                    youTubePlayer.play()
                    Log.e("Youtube", "Đang phát")
                }
            })
        }

        override fun onDestroy() {
            super.onDestroy()
            videoPlayerView.release()
        }
    }
}