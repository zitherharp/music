package com.zitherharp.music.video.ui.hashtag

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getVideos
import com.zitherharp.music.model.Video
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter
import com.zitherharp.music.video.ArtistActivity
import com.zitherharp.music.video.databinding.ArtistListItemBinding
import com.zitherharp.music.video.databinding.ItemListContentBinding
import com.zitherharp.music.video.ui.artist.ArtistListAdapter
import kotlin.reflect.typeOf

class Item {

    abstract class ItemListAdapter(context: Context,
                                   items: List<Spreadsheet>) :
        RecyclerViewAdapter<ItemListAdapter.ItemListContent>(context, items) {

        class ItemListContent(binding: ItemListContentBinding) : RecyclerView.ViewHolder(binding.root) {
            val itemImage = binding.itemImage
            val itemTitle = binding.itemTitle
            val itemSubtitle = binding.itemSubtitle
            val itemOption = binding.itemOption
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemListContent(
                ItemListContentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    class ArtistListAdapter(private val context: Context,
                            private val artists: List<Artist>) : ItemListAdapter(context, artists) {

        override fun onBindViewHolder(holder: ItemListContent, position: Int) {
            with(holder) {
                val artist = artists[position]
                itemImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
                itemTitle.text = artist.getName(Language.VIETNAMESE)
                itemSubtitle.text = String.format("${artist.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, ArtistActivity::class.java).apply {
                        putExtra(ArtistActivity::class.java.name, artist.id)
                    })
                }
            }
        }
    }
}