package com.zitherharp.music.shorts.ui.shorts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.zitherharp.music.R
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getShorts
import com.zitherharp.music.shorts.databinding.ShortFullscreenActivityBinding

class ShortFullscreenActivity: AppCompatActivity() {
    private val binding: ShortFullscreenActivityBinding by lazy { ShortFullscreenActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val shorts = intent.getStringExtra(ShortFullscreenActivity::class.qualifiedName)!!.getShorts()
        val shortPosition = intent.getIntExtra(ShortFullscreenActivity::class.simpleName, 0)
        with(binding) {
            setContentView(root)
            with(shortList) {
                PagerSnapHelper().attachToRecyclerView(this)
                adapter = ShortFullscreenAdapter(this@ShortFullscreenActivity, shorts)
                scrollToPosition(shortPosition)
            }
        }
    }
}