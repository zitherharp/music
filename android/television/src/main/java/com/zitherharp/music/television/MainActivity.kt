package com.zitherharp.music.television

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zitherharp.music.television.ui.vertical.VerticalFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, VerticalFragment())
                .commitNow()
        }
    }
}