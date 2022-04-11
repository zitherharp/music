package com.zitherharp.music.videotv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class MainActivity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LauncherActivity::class.java))
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MainFragment())
            .commitNow()
    }
}