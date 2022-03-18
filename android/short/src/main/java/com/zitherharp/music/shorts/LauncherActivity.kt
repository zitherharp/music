package com.zitherharp.music.shorts

import android.content.Intent
import android.os.Bundle
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.ui.activity.LauncherActivity
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class LauncherActivity: LauncherActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBind(Short::class.java.name)
    }

    override fun onPrepare() {
        Artist.repository
        Audio.repository
        Short.repository
    }

    override fun onLaunch() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}