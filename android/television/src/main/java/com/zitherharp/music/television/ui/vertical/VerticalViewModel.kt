package com.zitherharp.music.television.ui.vertical

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Music
import com.zitherharp.music.model.Video

class VerticalViewModel(music: Music): ViewModel() {
    private lateinit var repository: MutableMap<String, out Youtube>

    val playlist: MutableLiveData<ArrayList<Youtube>> by lazy {
        MutableLiveData<ArrayList<Youtube>>().also {
            fill()
        }
    }

    val playingSong: MutableLiveData<Youtube> by lazy {
        MutableLiveData<Youtube>().also {
            it.value = repository.values.random()
        }
    }

    init {
        when (music) {
            Music.AUDIO -> repository = Audio.repository
            Music.VIDEO -> repository = Video.repository
            else -> {}
        }
    }

    private fun fill() {
        playlist.value?.let { item ->
            var youtube: Youtube
            while (item.size < 15) {
                youtube = repository.values.random()
                if (item.contains(youtube)) continue
                item.add(youtube)
            }
        }
    }

    fun play() {
        playingSong.value = Audio.repository.values.first()
        playlist.value?.removeAt(0)
    }
}