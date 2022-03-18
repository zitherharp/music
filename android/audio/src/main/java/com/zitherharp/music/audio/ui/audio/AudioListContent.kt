package com.zitherharp.music.audio.ui.audio

import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.audio.databinding.AudioListContentBinding

class AudioListContent(binding: AudioListContentBinding): RecyclerView.ViewHolder(binding.root) {
    val image = binding.audioImage
    val title = binding.audioVietnameseName
    val subtitle = binding.audioChineseName
}