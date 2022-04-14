package com.zitherharp.music.core

import com.zitherharp.music.model.Artist
import java.util.concurrent.TimeUnit

abstract class Youtube(id: String) : Spreadsheet(id) {
    var duration = 0L
    protected var viewCount = 0
    protected var commentCount = 0

    lateinit var artistId: String

    companion object {
        const val ARTIST_ID = 1
        const val AUDIO_ID = 2
        const val DURATION = 4

        fun List<Youtube>.toString(language: Language, splitChar: String = COMBINE_CHAR): String {
            var string = EMPTY_CHAR
            forEach {
                string += splitChar + it.toString(language)
            }
            return string
        }
    }

    enum class Image(val width: Int, val height: Int) {
        DEFAULT(120, 90),
        MQDEFAULT(320, 180),
        HQDEFAULT(480, 360),
        SDDEFAULT(640, 480),
        MAXRESDEFAULT(1280, 720)
    }

    enum class Url {
        DEFAULT,
        SHORTEN,
        EMBED
    }

    fun toString(language: Language) =
        getName(language) + CONCAT_CHAR + getArtists().getName(language)

    fun getImageUrl(image: Image) =
        "https://i.ytimg.com/vi/$id/${image.name.lowercase()}.jpg"

    fun getShareUrl(url: Url = Url.SHORTEN) = when (url) {
        Url.SHORTEN -> "https://youtu.be/$id"
        Url.DEFAULT -> "https://youtube.com/watch?v=$id"
        Url.EMBED -> "https://www.youtube.com/embed/$id"
    }

    fun getDuration() = String.format("%02d:%02d",
        TimeUnit.SECONDS.toMinutes(duration),
        TimeUnit.SECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(duration)))

    fun getHashTag(language: Language): String {
        var hashTags = "#${getName(language)}"
        for (artist in getArtists()) {
            hashTags += SPACE_CHAR + "#${artist.getName(language)}"
        }
        return hashTags
    }

    fun getArtists(): List<Artist> {
        return ArrayList<Artist>().apply {
            artistId.split(SPLIT_CHAR).forEach { id ->
                Artist.repository[id]?.let { artist ->
                    add(artist)
                }
            }
        }
    }
}
