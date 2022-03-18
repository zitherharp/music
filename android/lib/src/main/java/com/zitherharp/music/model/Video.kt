package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

class Video(id: String,
            private val artistId: String):
    Youtube(id) {

    companion object {
        private const val ARTIST_ID = 1
        private const val AUDIO_ID = 2

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
                    jsonValue.requireString(ID),
                    jsonValue.requireString(ARTIST_ID)).apply {
                        chineseName = jsonValue.requireString(CHINESE_NAME)
                        vietnameseName = jsonValue.requireString(VIETNAMESE_NAME)
                        chineseDescription = jsonValue.requireString(CHINESE_DESCRIPTION)
                        vietnameseDescription = jsonValue.requireString(VIETNAMESE_DESCRIPTION)
                }
            }
        }
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