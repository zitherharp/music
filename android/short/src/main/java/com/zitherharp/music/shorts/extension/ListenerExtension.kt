package com.zitherharp.music.shorts.extension

import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class ListenerExtension(
    private val context: Context,
    private val recyclerView: RecyclerView) : PagerSnapHelper() {
    private var layoutManager = recyclerView.layoutManager as LinearLayoutManager

    override fun onFling(velocityX: Int, velocityY: Int): Boolean {
        val p = layoutManager.findFirstVisibleItemPosition()
        Log.e("position:", p.toString())
        Toast.makeText(context, layoutManager.findViewByPosition(p).toString(), Toast.LENGTH_SHORT).show()
        Log.e("layout:", layoutManager.findViewByPosition(p).toString())
        Log.e("layoutchild:", (layoutManager.findViewByPosition(p) as FrameLayout)[0].toString())
        val shortView = (layoutManager.findViewByPosition(p) as FrameLayout)[0] as YouTubePlayerView
        shortView.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.play()
            }
        })
        return super.onFling(velocityX, velocityY)
    }
}