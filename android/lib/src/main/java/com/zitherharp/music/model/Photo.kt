package com.zitherharp.music.model

import com.zitherharp.music.core.Pinterest

class Photo(id: String, private val artistId: String): Pinterest(id) {

    companion object {
        private const val ARTIST_ID = 1

        val repository = HashMap<String, Photo>()

        init {
            val jsonValues = getJsonValues(Photo::class.java.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Photo(
                    jsonValue.requireString(ID),
                    jsonValue.requireString(ARTIST_ID)).apply {
                        chineseName = jsonValue.requireString(CHINESE_NAME)
                        vietnameseName = jsonValue.requireString(VIETNAMESE_NAME)
                        chineseDescription = jsonValue.requireString(CHINESE_DESCRIPTION)
                        vietnameseDescription = jsonValue.requireString(VIETNAMESE_DESCRIPTION)
                }
            }
        }

        fun String?.getPhotos() =
            ArrayList<Photo>().apply {
                if (!isNullOrBlank()) {
                    for (id in split(SPLIT_CHAR)) {
                        repository[id]?.let { photo ->
                            add(photo)
                        }
                    }
                }
            }
    }

    fun getArtists(): List<Artist> {
        val artists = ArrayList<Artist>()
        for (id in artistId.split(SPLIT_CHAR)) {
            for (artist in Artist.repository.values) {
                if (artist.id == id) {
                    artists.add(artist)
                    break
                }
            }
        }
        return artists
    }
}