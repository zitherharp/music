package com.zitherharp.music

import android.content.Context
import android.content.SharedPreferences
import com.zitherharp.music.core.Spreadsheet.Companion.EMPTY_CHAR
import com.zitherharp.music.core.Spreadsheet.Companion.SPLIT_CHAR
import com.zitherharp.music.model.*

open class Preference(protected val context: Context) {
    protected val preferences: SharedPreferences =
        context.getSharedPreferences(Preference::class.java.name, Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "userId"
        const val USER_NAME = "userName"
        const val PHOTO_ID = "photoId"
        const val ALBUM_ID = "albumId"
        const val ARTIST_ID = "artistId"
        const val SEARCH_QUERY = "searchQuery"

        fun Preference.getAlbums(): List<Album> =
            ArrayList<Album>().apply {
                getArrayList(ALBUM_ID).forEach { id ->
                    Album.repository[id]?.let { item ->
                        add(item)
                    }
                }
            }

        fun Preference.getArtists(): List<Artist> =
            ArrayList<Artist>().apply {
                getArrayList(ARTIST_ID).forEach { id ->
                    Artist.repository[id]?.let { item ->
                        add(item)
                    }
                }
            }
    }

    fun get(key: String) = preferences.getString(key, null)

    fun getArrayList(key: String) =
        ArrayList<String>().apply {
            get(key)?.split(key)?.let { addAll(it) }
        }

    /**
    Check a [value] by the [key]
     */
    fun contains(key: String, value: String) =
        preferences.getString(key, null)?.contains(value) ?: false

    /**
    Add a value by the key
     */
    fun add(key: String, value: String) {
        preferences.edit().apply {
            val item = preferences.getString(key, null)
            if (!item.isNullOrEmpty()) {
                putString(key, item + value + SPLIT_CHAR)
            } else {
                putString(key, value + SPLIT_CHAR)
            }
        }.apply()
    }

    /**
    Remove a value by the key
     */
    fun remove(key: String, value: String) {
        preferences.edit().apply {
            val item = preferences.getString(key, null)
            if (!item.isNullOrEmpty()) {
                putString(key, item.replace(value + SPLIT_CHAR, EMPTY_CHAR))
            }
        }.apply()
    }

    /**
    Clear all values by the [key]
     */
    fun remove(key: String) {
        preferences.edit().apply {
            remove(key)
        }.apply()
    }

    /**
    Replace old value by new [value]
     */
    fun replace(key: String, value: String) {
        preferences.edit().apply {
            putString(key, value)
        }.apply()
    }

    fun clear() {
        preferences.edit().apply {
            clear()
        }.apply()
    }
}
