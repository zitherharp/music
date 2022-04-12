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

        fun String.getArtists(): List<Artist> {
            return ArrayList<Artist>().apply {
                split(SPLIT_CHAR).forEach { id ->
                    repository[id]?.let { artist ->
                        add(artist)
                    }
                }
            }
        }

        fun Artist.getShorts(): List<Short> {
            return ArrayList<Short>().apply {
                Short.repository.values.forEach { short ->
                    if (short.artistId.contains(id)) {
                        add(short)
                    }
                }
            }
        }

        fun Artist.getAudios(): List<Audio> {
            return ArrayList<Audio>().apply {
                Audio.repository.values.forEach { audio ->
                    if (audio.artistId.contains(id)) {
                        add( audio)
                    }
                }
            }
        }

        fun Artist.getVideos(): List<Video> {
            return ArrayList<Video>().apply {
                Video.repository.values.forEach { video ->
                    if (video.artistId.contains(id)) {
                        add(video)
                    }
                }
            }
        }

        fun List<Artist>.getVideos(isGetAll: Boolean): List<Video> {
            return ArrayList<Video>().apply {
                if (isGetAll) {
                    this@getVideos.forEach { artist ->
                        addAll(artist.getVideos())
                    }
                } else {
                    Video.repository.values.forEach { video ->
                        if (video.artistId == getId()) {
                            add(video)
                        }
                    }
                }
            }
        }
    }
}