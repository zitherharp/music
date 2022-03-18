package com.zitherharp.music.shorts.ui.shorts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Audio.Companion.toString
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortFullscreenContentBinding
import com.zitherharp.music.shorts.extension.Extension.onArtistDetailActivity
import com.zitherharp.music.shorts.extension.Extension.onAudioDetailActivity
import com.zitherharp.music.shorts.extension.Extension.onFavourite
import com.zitherharp.music.shorts.extension.Extension.onShare
import com.zitherharp.music.shorts.model.User
import com.zitherharp.music.shorts.ui.audio.AudioListContent
import com.zitherharp.music.shorts.ui.comment.CommentListDialog
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class ShortFullscreenAdapter(private val activity: AppCompatActivity,
                             private val shorts: List<Short>):
    RecyclerViewAdapter<ShortFullscreenAdapter.ViewHolder>(activity.baseContext, shorts) {

    inner class ViewHolder(binding: ShortFullscreenContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val shortView = binding.shortView
        val artistImage = binding.artistImage
        val artistName = binding.artistName
        val audioName = binding.audioName
        val favouriteButton = binding.favouriteButton
        val commentButton = binding.commentButton
        val shareButton = binding.shareButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ShortFullscreenContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val short = shorts[position]
            val audios = short.getAudios()
            val artists = short.getArtists()
            with(shortView) {
                activity.lifecycle.addObserver(this)
                addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(short.id, 0F)
                        youTubePlayer.pause()
                    }
                    override fun onStateChange(youTubePlayer: YouTubePlayer,
                                               state: PlayerConstants.PlayerState) {
                        if (state == PlayerConstants.PlayerState.ENDED) {
                            youTubePlayer.play()
                        }
                    }
                })
            }
            with(artistImage) {
                setImageUrl(artists.first().getImageUrl(QQMusic.Image.SMALL))
                onArtistDetailActivity(activity.supportFragmentManager, artists)
            }
            with(artistName) {
                text = artists.getName(Language.VIETNAMESE)
                onArtistDetailActivity(activity.supportFragmentManager, artists)
            }
            with(audioName) {
                isSelected = true
                text = if (audios.isNotEmpty())
                    audios.toString(Language.VIETNAMESE) else "Bài hát của ${artistName.text}"
            }
            audioName.onAudioDetailActivity(activity.supportFragmentManager, audios)
            favouriteButton.onFavourite(User.SHORT_ID, short.id)
            shareButton.onShare(short)
            commentButton.setOnClickListener {
                CommentListDialog().showNow(activity.supportFragmentManager, AudioListContent::class.java.name)
            }
        }
    }
}