package com.zitherharp.music.shorts.ui.artist

import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.shorts.databinding.ArtistListContentBinding

class ArtistListContent(binding: ArtistListContentBinding): RecyclerView.ViewHolder(binding.root) {
    val image = binding.image
    val title = binding.title
    val subtitle = binding.subtitle
}