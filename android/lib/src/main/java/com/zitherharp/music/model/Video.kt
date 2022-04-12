package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

class Video(id: String): Youtube(id) {
    lateinit var audioId: String

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
                    jsonValue.requireString(ID)).apply {
                        artistId = jsonValue.requireString(ARTIST_ID)
                        audioId = EMPTY_CHAR
                        chineseName = jsonValue.requireString(CHINESE_NAME)
                        vietnameseName = jsonValue.requireString(VIETNAMESE_NAME)
                        chineseDescription = jsonValue.requireString(CHINESE_DESCRIPTION)
                        vietnameseDescription = jsonValue.requireString(VIETNAMESE_DESCRIPTION)
                        duration = jsonValue.requireLong(DURATION)
                }
            }
        }

        fun String.getVideos(): List<Video> {
            return ArrayList<Video>().apply {
                split(SPLIT_CHAR).forEach { id ->
                    repository[id]?.let { video ->
                        add(video)
                    }
                }
            }
        }
    }

    fun getAudios(): List<Audio> {
        return ArrayList<Audio>().apply {
            audioId.split(SPLIT_CHAR).forEach { id ->
                Audio.repository[id]?.let { audio ->
                    add(audio)
                }
            }
        }
    }
}