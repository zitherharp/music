package com.zitherharp.music.video

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.model.*
import com.zitherharp.music.model.Video.Companion.getVideos
import com.zitherharp.music.video.databinding.ActivityHashtagBinding
import com.zitherharp.music.video.ui.hashtag.ItemListDialog
import com.zitherharp.music.video.ui.video.VideoListAdapter

class HashtagActivity : AppCompatActivity() {
    private val binding: ActivityHashtagBinding by lazy { ActivityHashtagBinding.inflate(layoutInflater) }

    companion object {
        const val HASHTAG_TITLE = "title"
        const val HASHTAG_VIDEOS = "videos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with (binding) {
            setContentView(root)
            intent.getStringExtra(HASHTAG_VIDEOS)?.let { videoIds ->
                val videos = videoIds.getVideos()
                hashtagTitle.text = intent.getStringExtra(HASHTAG_TITLE)
                hashtagSubtitle.text = String.format("${videos.size} video")
                videoList.run {
                    adapter = VideoListAdapter(baseContext, videos)
                    layoutManager = GridLayoutManager(baseContext, 2)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class ArtistListAdapter(private val context: Context,
                            private val artists: List<Artist>) : ItemListDialog.ItemListAdapter(context, artists) {

        override fun onBindViewHolder(holder: ItemListContent, position: Int) {
            with(holder) {
                val artist = artists[position]
                itemImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
                itemTitle.text = String.format("#${artist.getName(Language.CHINESE)}")
                itemSubtitle.text = String.format("${artist.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, HashtagActivity::class.java).apply {
                        putExtra(HASHTAG_TITLE, itemTitle.text)
                        putExtra(HASHTAG_VIDEOS, artist.getVideos().getId())
                    })
                }
            }
        }
    }

    class AudioListAdapter(private val context: Context,
                           private val audios: List<Audio>) : ItemListDialog.ItemListAdapter(context, audios) {

        override fun onBindViewHolder(holder: ItemListContent, position: Int) {
            with(holder) {
                val audio = audios[position]
                itemImage.setImageUrl(audio.getImageUrl(Youtube.Image.DEFAULT))
                itemTitle.text = String.format("#${audio.getName(Language.CHINESE)}")
                itemSubtitle.text = String.format("${audio.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, HashtagActivity::class.java).apply {
                        putExtra(HASHTAG_TITLE, itemTitle.text)
                        putExtra(HASHTAG_VIDEOS, audio.getVideos().getId())
                    })
                }
            }
        }
    }
}