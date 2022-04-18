package com.zitherharp.music.video.ui.hashtag

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getVideos
import com.zitherharp.music.model.Audio
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter
import com.zitherharp.music.video.ArtistActivity
import com.zitherharp.music.video.HashtagActivity
import com.zitherharp.music.video.databinding.HashtagListContentBinding

abstract class HashtagListAdapter(context: Context,
                                  items: List<Spreadsheet>) :
    RecyclerViewAdapter<HashtagListAdapter.HashtagListContent>(context, items) {

    class HashtagListContent(binding: HashtagListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val hashtagImage = binding.hashtagImage
        val hashtagTitle = binding.hashtagTitle
        val hashtagSubtitle = binding.hashtagSubtitle
        //val hashtagOption = binding.hashtagOption
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HashtagListContent(
            HashtagListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    class AudioListAdapter(private val context: Context,
                           private val audios: List<Audio>) :
        HashtagListAdapter(context, audios) {

        override fun onBindViewHolder(holder: HashtagListContent, position: Int) {
            with(holder) {
                val audio = audios[position]
                hashtagImage.setImageUrl(audio.getImageUrl(Youtube.Image.DEFAULT))
                hashtagTitle.text = String.format("#${audio.getName(Language.CHINESE)}")
                hashtagSubtitle.text = String.format("${audio.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, HashtagActivity::class.java).apply {
                        putExtra(HashtagActivity.HASHTAG_TITLE, hashtagTitle.text)
                        putExtra(HashtagActivity.HASHTAG_VIDEOS, audio.getVideos().getId())
                    })
                }
            }
        }
    }

    class ArtistListAdapter(private val context: Context,
                            private val artists: List<Artist>) :
        HashtagListAdapter(context, artists) {

        override fun onBindViewHolder(holder: HashtagListContent, position: Int) {
            with(holder) {
                val artist = artists[position]
                hashtagImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
                hashtagTitle.text = String.format("#${artist.getName(Language.CHINESE)}")
                hashtagSubtitle.text = String.format("${artist.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, HashtagActivity::class.java).apply {
                        putExtra(HashtagActivity.HASHTAG_TITLE, hashtagTitle.text)
                        putExtra(HashtagActivity.HASHTAG_VIDEOS, artist.getVideos().getId())
                    })
                }
            }
        }
    }
}