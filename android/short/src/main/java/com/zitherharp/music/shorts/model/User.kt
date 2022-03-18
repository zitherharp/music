package com.zitherharp.music.shorts.model

import android.content.Context
import com.zitherharp.music.R
import com.zitherharp.music.core.Spreadsheet.Companion.EMPTY_CHAR
import com.zitherharp.music.core.Spreadsheet.Companion.SPLIT_CHAR
import java.util.*

data class User(val context: Context) {
    private val preferences =
        context.getSharedPreferences(User::class.java.name, Context.MODE_PRIVATE)

    val id = preferences.getString(ID, UUID.randomUUID().toString())
    val name = preferences.getString(NAME, context.getString(R.string.short_app_name))
    val shortId = preferences.getString(SHORT_ID, null)
    val audioId = preferences.getString(AUDIO_ID, null)
    val artistId = preferences.getString(ARTIST_ID, null)

    companion object {
        const val ID = "userId"
        const val NAME = "userName"
        const val SHORT_ID = "userShortId"
        const val AUDIO_ID = "userAudioId"
        const val ARTIST_ID = "userArtistId"
    }

    fun contains(key: String, value: String) =
        preferences.getString(key, null)?.contains(value) ?: false

    fun add(key: String, value: String) {
        val item = preferences.getString(key, null)
        preferences.edit().apply {
            if (!item.isNullOrEmpty()) {
                putString(key, item + value + SPLIT_CHAR)
            } else {
                putString(key, value + SPLIT_CHAR)
            }
        }.apply()
    }

    fun remove(key: String, value: String) {
        val item = preferences.getString(key, null)
        preferences.edit().apply {
            if (!item.isNullOrEmpty()) {
                putString(key, item.replace(value + SPLIT_CHAR, EMPTY_CHAR))
            }
        }.apply()
    }

    fun edit(key: String, value: String) {
        preferences.edit().apply {
            putString(key, value)
        }.apply()
    }

    fun clear(key: String) {
        preferences.edit().apply {
            putString(key, null)
        }.apply()
    }
}