package com.zitherharp.music.tv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Video
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class MainActivity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, LauncherActivity::class.java))
    }

    override fun onRestart() {
        super.onRestart()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_browse_fragment, MainFragment())
            .commitNow()
    }
}