package com.zitherharp.music.photo.tv.activity

import android.content.Intent
import android.os.Bundle
import android.telecom.Connection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import com.zitherharp.music.photo.tv.R

class MainActivity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, LauncherActivity::class.java))
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, BrowserFragment())
            .commitNow()
    }

    inner class BrowserFragment: BrowseSupportFragment() {
        private lateinit var backgroundManager: BackgroundManager
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            backgroundManager = BackgroundManager.getInstance(activity).apply {
                attach(this@MainActivity.window)
            }
            headersState = HEADERS_ENABLED
            isHeadersTransitionOnBackEnabled = true
            title = getString(com.zitherharp.music.R.string.photo_app_name)
            brandColor = ContextCompat.getColor(requireActivity(), com.zitherharp.music.R.color.photo_app_color)
            searchAffordanceColor = ContextCompat.getColor(requireActivity(), com.zitherharp.music.R.color.black)
        }
    }
}