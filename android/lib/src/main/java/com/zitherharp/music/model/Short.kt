package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

class Short(id: String,
            val artistId: String,
            val audioId: String): Youtube(id) {

    companion object {
        private const val ARTIST_ID = 1
        private const val AUDIO_ID = 2

        val repository: MutableMap<String, Short> = HashMap()

        init {
            val jsonValues = getJsonValues(Short::class.java.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Short(
                    jsonValue.requireString(ID),
                    jsonValue.requireString(ARTIST_ID),
                    jsonValue.requireString(AUDIO_ID))
            }
        }

        fun String?.getShorts(): List<Short> {
            val shorts = ArrayList<Short>()
            if (!this.isNullOrBlank()) {
                for (id in split(SPLIT_CHAR)) {
                    repository[id]?.let {
                        shorts.add(it)
                    }
                }
            }
            return shorts
        }
    }

    fun getShareUrl() =
        "https://youtube.com/shorts/$id"

    fun getAudios(): List<Audio> {
        val audios = ArrayList<Audio>()
        for (audioId in audioId.split(SPLIT_CHAR)) {
            for (audio in Audio.repository.values) {
                if (audio.id == audioId) {
                    audios.add(audio)
                    break
                }
            }
        }
        return audios
    }

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
}