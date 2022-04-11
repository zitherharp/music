package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

data class Television(val repository: MutableMap<String, out Youtube>) {
    private var playingSong: Youtube
    lateinit var playlist: ArrayList<Youtube>

    init {
        fill()
        playingSong = repository.values.random()
    }

    private fun fill() {
        var youtube: Youtube
        while (playlist.size < 15) {
            youtube = repository.values.random()
            if (playlist.contains(youtube)) continue
            playlist.add(youtube)
        }
    }

    fun play() {
        playingSong = playlist[0]
        playlist.removeAt(0)
        fill()
    }
}