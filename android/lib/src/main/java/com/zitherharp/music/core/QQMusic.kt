package com.zitherharp.music.core

abstract class QQMusic(id: String): Spreadsheet(id) {

    enum class Image(val pixel: Int) {
        SMALL(300),
        MEDIUM(500),
        LARGE(800)
    }

    abstract fun getImageUrl(image: Image): String
}