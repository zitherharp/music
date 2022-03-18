package com.zitherharp.music.model

import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Youtube

class Audio(id: String, val artistId: String): Youtube(id) {

    companion object {
        private const val ARTIST_ID = 1

        val repository: MutableMap<String, Audio> = HashMap()

        init {
            val jsonValues = getJsonValues(Audio::class.java.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Audio(
                    jsonValue.requireString(ID),
                    jsonValue.requireString(ARTIST_ID)).apply {
                        chineseName = jsonValue.requireString(CHINESE_NAME)
                        vietnameseName = jsonValue.requireString(VIETNAMESE_NAME)
                        chineseDescription = jsonValue.requireString(CHINESE_DESCRIPTION)
                        vietnameseDescription = jsonValue.requireString(VIETNAMESE_DESCRIPTION)
                }
            }
        }

        fun String?.getAudios(): List<Audio> {
            val audios = ArrayList<Audio>()
            if (!isNullOrBlank()) {
                for (id in split(SPLIT_CHAR)) {
                    repository[id]?.let {
                        audios.add(it)
                    }
                }
            }
            return audios
        }

        fun List<Audio>.toString(language: Language): String {
            var string = EMPTY_CHAR
            forEach {
                string += it.toString(language) + COMBINE_CHAR
            }
            return string.removeSuffix(COMBINE_CHAR)
        }
    }

    fun toString(language: Language) =
        getName(language) + CONCAT_CHAR + getArtists().getName(language)

    fun getArtists(): List<Artist> {
        val artists = ArrayList<Artist>()
        for (artistId in artistId.split(SPLIT_CHAR)) {
            for (artist in Artist.repository.values) {
                if (artist.id == artistId) {
                    artists.add(artist)
                    break
                }
            }
        }
        return artists
    }

    fun getShorts(): List<Short> {
        val shorts = ArrayList<Short>()
        for (short in Short.repository.values) {
            for (audioId in short.audioId.split(SPLIT_CHAR)) {
                if (audioId == id) {
                    shorts.add(short)
                }
            }
        }
        return shorts
    }
}