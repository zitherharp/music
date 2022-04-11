package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

class Short(id: String, val audioId: String): Youtube(id) {

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
                    jsonValue.requireString(AUDIO_ID)).apply {
                        artistId = jsonValue.requireString(ARTIST_ID)
                }
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

    fun getShareUrl() = "https://youtube.com/shorts/$id"

    fun getAudios(): List<Audio> {
        return ArrayList<Audio>().apply {
            for (audioId in audioId.split(SPLIT_CHAR)) {
                Audio.repository[audioId]?.let { audio -> add(audio) }
            }
        }
    }
}