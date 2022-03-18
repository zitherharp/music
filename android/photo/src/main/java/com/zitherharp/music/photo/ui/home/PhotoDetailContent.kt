package com.zitherharp.music.photo.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.photo.databinding.PhotoDetailContentBinding

class PhotoDetailContent(binding: PhotoDetailContentBinding): RecyclerView.ViewHolder(binding.root) {
    // TODO: Photo
    val photoImage = binding.photoImage
    val photoMenuButton = binding.photoMenuButton
    val photoChineseName = binding.photoChineseName
    val photoVietnameseName = binding.photoVietnameseName
    val photoRecyclerView = binding.photoRecyclerView

    // TODO: Artist
    val artistImage = binding.artistImage
    val artistChineseName = binding.artistChineseName
    val artistVietnameseName = binding.artistVietnameseName

    // TODO: Buttons
    val backButton = binding.backButton
}