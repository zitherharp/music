package com.zitherharp.music.core

abstract class Pinterest(id: String): Spreadsheet(id) {

    enum class Image(val pixel: Int) {
        SMALL(236),
        MEDIUM(564),
        LARGE(736),
        ORIGINALS(1200)
    }

    fun getImageUrl(image: Image): String {
        var prefix = SPLIT_CHAR
        for (i in 0 until 6 step 2) {
            prefix += "${id[i]}${id[i+1]}$SPLIT_CHAR"
        }
        return "https://i.pinimg.com/${image.pixel}x$prefix$id.jpg"
    }
}