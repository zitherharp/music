package com.zitherharp.music.shorts.ui.audio

import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.shorts.databinding.AudioListContentBinding

class AudioListContent(binding: AudioListContentBinding): RecyclerView.ViewHolder(binding.root) {
    val image = binding.audioImage
    val title = binding.audioVietnameseName
    val subtitle = binding.audioChineseName
}