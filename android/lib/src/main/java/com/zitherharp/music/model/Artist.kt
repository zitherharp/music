package com.zitherharp.music.model

import com.zitherharp.music.core.QQMusic

class Artist(id: String): QQMusic(id) {

    override fun getImageUrl(image: Image) =
        "https://y.qq.com/music/photo_new/T001R${image.pixel}x${image.pixel}M000$id.jpg"

    companion object {
        val repository: MutableMap<String, Artist> = HashMap()

        init {
            val jsonValues = getJsonValues(Artist::class.java.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Artist(
                    jsonValue.requireString(ID)).apply {
                        chineseName = jsonValue.requireString(CHINESE_NAME)
                        vietnameseName = jsonValue.requireString(VIETNAMESE_NAME)
                        chineseDescription = jsonValue.requireString(CHINESE_DESCRIPTION)
                        vietnameseDescription = jsonValue.requireString(VIETNAMESE_DESCRIPTION)
                }
            }
        }

        fun String?.getArtists(): List<Artist> {
            val artists = ArrayList<Artist>()
            if (!isNullOrBlank()) {
                for (id in split(SPLIT_CHAR)) {
                    repository[id]?.let {
                        artists.add(it)
                    }
                }
            }
            return artists
        }
    }

    fun getShorts(): List<Short> {
        val shorts = ArrayList<Short>()
        for (short in Short.repository.values) {
            for (artistId in short.artistId.split(SPLIT_CHAR)) {
                if (artistId == id) {
                    shorts.add(short)
                }
            }
        }
        return shorts
    }

    fun getAudios(): List<Audio> {
        val audios = ArrayList<Audio>()
        for (audio in Audio.repository.values) {
            for (artistId in audio.artistId.split(SPLIT_CHAR)) {
                if (artistId == id) {
                    audios.add(audio)
                }
            }
        }
        return audios
    }
}