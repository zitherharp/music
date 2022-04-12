package com.zitherharp.music.video.ui.artist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getVideos
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter
import com.zitherharp.music.video.ArtistActivity
import com.zitherharp.music.video.databinding.ArtistListItemBinding

class ArtistListAdapter(private val context: Context,
                        private val artists: List<Artist>):
    RecyclerViewAdapter<ArtistListAdapter.ArtistListItem>(context, artists) {

    class ArtistListItem(binding: ArtistListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val artistImage = binding.artistImage
        val artistTitle = binding.artistTitle
        val artistSubtitle = binding.artistSubtitle
        val artistOption = binding.artistOption
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArtistListItem(
            ArtistListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ArtistListItem, position: Int) {
        with(holder) {
            val artist = artists[position]
            artistImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
            artistTitle.text = artist.getName(Language.VIETNAMESE)
            artistSubtitle.text = String.format("${artist.getVideos().size} video")
            itemView.setOnClickListener {
                context.startActivity(Intent(context, ArtistActivity::class.java).apply {
                    putExtra(ArtistActivity::class.java.name, artist.id)
                })
            }
        }
    }
}