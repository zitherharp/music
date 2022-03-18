package com.zitherharp.music.shorts.ui.audio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.R
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.AudioDetailActivityBinding
import com.zitherharp.music.shorts.extension.Extension.onArtistDetailActivity
import com.zitherharp.music.shorts.extension.Extension.onChineseName
import com.zitherharp.music.shorts.extension.Extension.onImage
import com.zitherharp.music.shorts.extension.Extension.onShare
import com.zitherharp.music.shorts.ui.artist.ArtistDetailActivity
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class AudioDetailActivity: AppCompatActivity() {
    private val binding: AudioDetailActivityBinding by lazy { AudioDetailActivityBinding.inflate(layoutInflater) }
    private lateinit var audio: Audio
    private lateinit var audios: List<Audio>
    private lateinit var shorts: List<Short>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        audio = Audio.repository[intent.getStringExtra(AudioDetailActivity::class.java.name)]!!
        with(binding) {
            setContentView(root)
            audios = emptyList()
            shorts = audio.getShorts()
            audioImage.onImage(audio)
            audioChineseName.onChineseName(audio)
            audioVietnameseName.text = audio.getName(Language.VIETNAMESE)
            artistName.text = audio.getArtists().getName(Language.VIETNAMESE)
            artistName.onArtistDetailActivity(supportFragmentManager, audio.getArtists())
            shareAudio.onShare(audio)
            AudioDetailAdapter(this@AudioDetailActivity,
                arrayOf("${getString(R.string.recommend)} ${audios.size}",
                    "${getString(R.string.use)} ${shorts.size}"))
                .attach(audioTabLayout, audioViewPager, 1)
        }
    }

    inner class AudioDetailAdapter(fragmentActivity: AudioDetailActivity,
                                   tabNames: Array<String>): FragmentStateAdapter(fragmentActivity, tabNames) {
        private val audioFragment = if (audios.isNotEmpty()) AudioListFragment(audios) else EmptyFragment()
        private val shortFragment = if (shorts.isNotEmpty()) ShortGridFragment(shorts) else EmptyFragment()

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return audioFragment
                1 -> return shortFragment
            }
            return super.createFragment(position)
        }
    }
}