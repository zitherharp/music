package com.zitherharp.music.audio

import android.content.Intent
import android.os.Bundle
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.ui.activity.LauncherActivity
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class LauncherActivity: LauncherActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBind(Audio::class.java.name)
    }

    override fun onPrepare() {
        Artist.repository
        Audio.repository
    }

    override fun onLaunch() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}