package com.zitherharp.music.video

import android.content.Intent
import android.os.Bundle
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Video
import com.zitherharp.music.ui.activity.LauncherActivity

class LauncherActivity: LauncherActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBind(Video::class.java.name)
    }

    override fun onPrepare() {
        Artist.repository
        Video.repository
    }

    override fun onLaunch() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}