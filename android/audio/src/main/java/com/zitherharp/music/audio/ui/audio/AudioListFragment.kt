package com.zitherharp.music.audio.ui.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zitherharp.music.audio.databinding.AudioListFragmentBinding
import com.zitherharp.music.model.Audio

class AudioListFragment(): Fragment() {
    private lateinit var binding: AudioListFragmentBinding
    private var audios: List<Audio> = ArrayList()

    constructor(audios: List<Audio>): this() {
        this.audios = audios
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        AudioListFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            audioList.adapter = AudioListAdapter(view.context, Audio.repository.values.toList().subList(0, 25))
        }
    }
}