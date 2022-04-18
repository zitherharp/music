package com.zitherharp.music.video

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.zitherharp.music.core.*
import com.zitherharp.music.model.Video
import com.zitherharp.music.model.Video.Companion.getVideos
import com.zitherharp.music.video.databinding.ActivityHashtagBinding
import com.zitherharp.music.video.ui.video.VideoVerticalAdapter
import java.lang.StringBuilder

class HashtagActivity : AppCompatActivity() {
    private val binding: ActivityHashtagBinding by lazy { ActivityHashtagBinding.inflate(layoutInflater) }

    companion object {
        const val HASHTAG_TITLE = "hashtagTitle"
        const val HASHTAG_VIDEOS = "hashtagVideos"

        fun Video.getHashtag(language: Language) =
            StringBuilder().apply {
                append("#${getName(language)}")
                getArtists().forEach { artist ->
                    append(Spreadsheet.SPACE_CHAR + "#${artist.getName(language)}")
                }
            }.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with (binding) {
            setContentView(root)
            intent.getStringExtra(HASHTAG_VIDEOS)?.let { videoIds ->
                val context = this@HashtagActivity
                val videos = videoIds.getVideos()
                title = intent.getStringExtra(HASHTAG_TITLE)
                videoList.run {
                    adapter = VideoVerticalAdapter(context, videos)
                    layoutManager = GridLayoutManager(context, 2)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}