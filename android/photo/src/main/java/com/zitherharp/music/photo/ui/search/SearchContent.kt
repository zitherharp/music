package com.zitherharp.music.photo.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.photo.databinding.SearchContentBinding

class SearchContent(binding: SearchContentBinding): RecyclerView.ViewHolder(binding.root) {
    val queryIcon = binding.queryIcon
    val querySearch = binding.querySearch
    val queryRemove = binding.queryRemove
}