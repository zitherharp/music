package com.zitherharp.music.video

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Extension.share
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.*
import com.zitherharp.music.model.Artist.Companion.getVideos
import com.zitherharp.music.video.databinding.ActivityVideoBinding
import com.zitherharp.music.video.ui.artist.ArtistListDialog
import com.zitherharp.music.video.ui.hashtag.ItemListDialog
import com.zitherharp.music.video.ui.video.*

class VideoActivity : AppCompatActivity() {
    private val binding: ActivityVideoBinding by lazy { ActivityVideoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Video.repository[intent.getStringExtra(VideoActivity::class.java.name)]?.let { video ->
            with(binding) {
                setContentView(root)
                // TODO: VideoPlayerView
                val context = this@VideoActivity.apply {
                    lifecycle.addObserver(videoPlayerView)
                }
                videoPlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadOrCueVideo(context.lifecycle, video.id, 0F)
                    }
                })
                // TODO: ArtistDetailLayout
                val artists = video.getArtists()
                val artist = artists.first()
                artistTitle.text = artists.getName(Language.VIETNAMESE, Spreadsheet.COMBINE_CHAR)
                artistSubtitle.text = String.format("${ArrayList<Video>().apply {
                    Video.repository.values.forEach { video ->
                        if (artists.getId() == video.artistId) {
                            add(video)
                        }
                    }
                }.size} video")
                artistImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
                artistLayout.setOnClickListener {
                    if (artists.size == 1) {
                        startActivity(Intent(context, ArtistActivity::class.java).apply {
                            putExtra(ArtistActivity::class.java.name, artist.id)
                        })
                    } else {
                        ArtistListDialog().apply {
                            isCancelable = false
                        }.showNow(supportFragmentManager, artists.getId())
                    }
                }
                // TODO: VideoDetailLayout
                videoTitle.text = video.getName(Language.VIETNAMESE)
                videoSubtitle.text = artists.getName(Language.VIETNAMESE)
                videoHashTag.run {
                    text = video.getHashTag(Language.CHINESE)
                    setOnClickListener {
                        ItemListDialog().apply {
                            isCancelable = false
                            arguments = Bundle().apply {
                                putString(VideoActivity::class.java.name, video.getAudios().getId())
                                putString(ArtistActivity::class.java.name, video.getArtists().getId())
                            }
                        }.showNow(supportFragmentManager, getString(com.zitherharp.music.R.string.tag))
                    }
                }
                // TODO: VideoButtonLayout
                shareVideo.setOnClickListener {
                    share(video.toString(Language.VIETNAMESE) + "\n" + video.getShareUrl())
                }
                downloadVideo.setOnClickListener {
                    Snackbar.make(it, "Không thể tải xuống video này", Snackbar.LENGTH_SHORT).show()
                }
                // TODO: VideoDetailLayout
                videoDetailLayout.setOnClickListener {
                    VideoDetailDialog().apply {
                        isCancelable = false
                    }.showNow(supportFragmentManager, video.id)
                }
                // TODO: VideoCommentLayout
                videoCommentLayout.setOnClickListener {
                    VideoCommentDialog().apply {
                        isCancelable = false
                    }.showNow(supportFragmentManager, video.id)
                }
                // TODO: VideoListLayout
                with(videoListLayout) {
                    videoList.adapter = VideoListAdapter(context, Video.repository.values.shuffled().take(10))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoPlayerView.run {
            release()
            lifecycle.removeObserver(this)
        }
    }
}