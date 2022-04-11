package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

class Video(id: String, private val audioId: String): Youtube(id) {

    companion object {
        val category = arrayOf(
            "Đề xuất",
            "Mới nhất",
            "Thịnh hành"
        )

        val repository: MutableMap<String, Video> = HashMap()

        init {
            val jsonValues = getJsonValues(Video::class.java.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Video(
                    jsonValue.requireString(ID), EMPTY_CHAR).apply {
                        artistId = jsonValue.requireString(ARTIST_ID)
                        chineseName = jsonValue.requireString(CHINESE_NAME)
                        vietnameseName = jsonValue.requireString(VIETNAMESE_NAME)
                        chineseDescription = jsonValue.requireString(CHINESE_DESCRIPTION)
                        vietnameseDescription = jsonValue.requireString(VIETNAMESE_DESCRIPTION)
                        duration = jsonValue.requireLong(DURATION)
                }
            }
        }
    }

    fun getAudios(): List<Audio> {
        return ArrayList<Audio>().apply {
            for (audioId in audioId.split(SPLIT_CHAR)) {
                Audio.repository[audioId]?.let { audio -> add(audio) }
            }
        }
    }
}