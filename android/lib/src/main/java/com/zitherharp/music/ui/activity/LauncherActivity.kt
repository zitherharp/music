package com.zitherharp.music.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.zitherharp.music.Extension.isNetworkConnected
import com.zitherharp.music.R
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Photo
import com.zitherharp.music.model.Short
import kotlinx.coroutines.*

@DelicateCoroutinesApi
abstract class LauncherActivity: AppCompatActivity() {
    private var isFirstLoad = true
    private lateinit var appIcon: ImageView
    private lateinit var appBackground: CoordinatorLayout

    protected abstract fun onPrepare()
    protected abstract fun onLaunch()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        supportActionBar?.hide()
        appIcon = findViewById(R.id.app_icon)
        appBackground = findViewById(R.id.app_background)
        appBackground.setOnClickListener { onLoad() }
    }

    protected fun onBind(appName: String) {
        when (appName) {
            Audio::class.java.name -> {
                appIcon.setImageResource(R.mipmap.ic_audio_launcher)
                appBackground.setBackgroundResource(R.color.audio_app_color)
            }
            Photo::class.java.name -> {
                appIcon.setImageResource(R.mipmap.ic_photo_launcher)
                appBackground.setBackgroundResource(R.color.photo_app_color)
            }
            Short::class.java.name -> {
                appIcon.setImageResource(R.mipmap.ic_short_launcher)
                appBackground.setBackgroundResource(R.color.short_app_color)
            }
        }
        onLoad()
    }

    private fun onLoad() {
        if (!isFirstLoad) return
        if (isNetworkConnected()) {
            isFirstLoad = false
            GlobalScope.launch(Dispatchers.IO) {
                onPrepare()
                withContext(Dispatchers.IO) {
                    onLaunch()
                    finish()
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show()
        }
    }
}