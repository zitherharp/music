package com.zitherharp.music.shorts.ui.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zitherharp.music.shorts.R
import com.zitherharp.music.shorts.databinding.CommentListFragmentBinding

class CommentListActivity: AppCompatActivity() {
    private val binding: CommentListFragmentBinding by lazy { CommentListFragmentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = getString(com.zitherharp.music.R.string.comment)
        }
    }
}