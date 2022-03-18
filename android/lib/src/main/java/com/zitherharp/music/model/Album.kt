package com.zitherharp.music.model

import com.zitherharp.music.core.QQMusic

class Album(id: String): QQMusic(id) {

    override fun getImageUrl(image: Image) =
        "https://y.qq.com/music/photo_new/T002R${image.pixel}x${image.pixel}M000$id.jpg"

    companion object {
        val repository = HashMap<String, Album>()

        fun String?.getAlbums(): List<Album> {
            val albums = ArrayList<Album>()
            if (!isNullOrBlank()) {
                for (id in split(SPLIT_CHAR)) {
                    repository[id]?.let { album ->
                        albums.add(album)
                    }
                }
            }
            return albums
        }
    }
}