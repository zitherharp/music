package com.zitherharp.music.tv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.OnboardingSupportFragment
import com.zitherharp.music.Extension.isNetworkConnected
import com.zitherharp.music.model.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LauncherActivity: FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, LauncherFragment())
            .commitNow()
    }

    class LauncherFragment: OnboardingSupportFragment() {
        override fun getPageCount() = 1

        override fun getPageTitle(pageIndex: Int): CharSequence {
            return getString(com.zitherharp.music.R.string.music_app_name)
        }

        override fun getPageDescription(pageIndex: Int): CharSequence {
            return getString(com.zitherharp.music.R.string.auto_generated_slogan)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            logoResourceId = com.zitherharp.music.R.mipmap.ic_music_launcher
        }

        override fun onCreateBackgroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
            return null
        }

        override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?): View {
            return ImageView(context).apply {
                scaleType = ImageView.ScaleType.FIT_CENTER
                setImageResource(com.zitherharp.music.R.mipmap.ic_music_launcher)
            }
        }

        override fun onCreateForegroundView(inflater: LayoutInflater?, container: ViewGroup?): View {
            return ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                //setImageResource(com.zitherharp.music.R.mipmap.ic_music_launcher)
            }
        }

        override fun onFinishFragment() {
            super.onFinishFragment()
            requireActivity().finish()
        }
    }
}
