package com.zitherharp.music.shorts.ui.artist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.shorts.databinding.ArtistListContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class ArtistListAdapter(private val context: Context,
                        private val artists: List<Artist>):
    RecyclerViewAdapter<ArtistListContent>(context, artists) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArtistListContent(
            ArtistListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ArtistListContent, position: Int) {
        with(holder) {
            with(artists[position]) {
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, ArtistDetailActivity::class.java).apply {
                        putExtra(ArtistDetailActivity::class.java.name, id)
                    })
                }
                image.setImageUrl(getImageUrl(QQMusic.Image.SMALL))
                title.text = getName(Language.VIETNAMESE)
                subtitle.text = getName(Language.CHINESE)
            }
        }
    }
}