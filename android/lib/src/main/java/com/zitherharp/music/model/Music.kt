package com.zitherharp.music.model

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.zitherharp.music.R

enum class Music {
    ALBUM,
    AUDIO,
    ARTIST,
    KARAOKE,
    PHOTO,
    SHORT,
    TELEVISION,
    VIDEO;

    companion object {
        fun getColor(music: Music): Int {
            return when (music) {
                AUDIO -> R.color.audio_app_color
                PHOTO -> R.color.photo_app_color
                SHORT -> R.color.short_app_color
                VIDEO -> R.color.video_app_color
                else -> R.color.music_app_color
            }
        }

        fun Music.getString(): Int {
            return when (this) {
                AUDIO -> R.string.audio_app_name
                PHOTO -> R.string.photo_app_name
                SHORT -> R.string.short_app_name
                else -> R.string.music_app_name
            }
        }

        fun getDrawable(context: Context, music: Music) =
            ContextCompat.getDrawable(context, when (music) {
                AUDIO -> R.mipmap.ic_audio_launcher
                PHOTO -> R.mipmap.ic_photo_launcher
                SHORT -> R.mipmap.ic_short_launcher
                VIDEO -> R.mipmap.ic_video_launcher
                else -> R.mipmap.ic_music_launcher
            })
    }
}