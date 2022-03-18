package com.zitherharp.music.audio

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.audio.databinding.ActivityMainBinding
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Audio

class MainActivity: AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
            appBarMain.run {
                setSupportActionBar(toolbar)
                navController = mainContainerFragment.getFragment<NavHostFragment>().navController
                navController.run {
                    navView.setupWithNavController(this)
                    setupActionBarWithNavController(this, appBarConfiguration)
                }
            }
            LocalBroadcastManager.getInstance(this@MainActivity)
                .registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    val audio = Audio.repository[intent.getStringExtra("audioId")]!!
                    val audioPosition = intent.getFloatExtra("audioPosition", 0f)
                    audioPlayer.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(audio.id, audioPosition)
                        }
                    })
                    with(appBarMain) {
                        audioPlayButton.setImageResource(android.R.drawable.ic_media_pause)
                        audioChineseName.text = audio.getName(Language.CHINESE)
                        audioVietnameseName.text = audio.getName(Language.VIETNAMESE)
                        audioImage.setImageUrl(audio.getImageUrl(Youtube.Image.MQDEFAULT))
                    }
                }
            }, IntentFilter("audioPlayer"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}