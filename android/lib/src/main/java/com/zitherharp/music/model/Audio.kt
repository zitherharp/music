package com.zitherharp.music.model

import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Youtube

class Audio(id: String): Youtube(id) {

    companion object {
        val repository: MutableMap<String, Audio> = HashMap()

        init {
            val jsonValues = getJsonValues(Audio::class.java.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Audio(
                    jsonValue.requireString(ID)).apply {
                        artistId = jsonValues.requireString(ARTIST_ID)
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