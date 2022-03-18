package com.zitherharp.music.core

abstract class Youtube(id: String): Spreadsheet(id) {
    var duration = 0
    var viewCount = 0
    var commentCount = 0

    companion object {
        const val DURATION = 4
    }

    enum class Image(val width: Int, val height: Int) {
        DEFAULT(120, 90),
        MQDEFAULT(320, 180),
        HQDEFAULT(480, 360),
        SDDEFAULT(640, 480),
        MAXRESDEFAULT(1280, 720)
    }

    fun getImageUrl(image: Image) =
        "https://i.ytimg.com/vi/$id/${image.name.lowercase()}.jpg"

    fun getShareUrl(isEmbed: Boolean = false) =
        if (!isEmbed) "https://youtu.be/$id" else "https://www.youtube.com/embed/$id"
}
