package com.zitherharp.music.shorts.ui.shorts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortFullscreenFragmentBinding

class ShortFullscreenFragment(): Fragment() {
    private lateinit var binding: ShortFullscreenFragmentBinding
    private var shorts: List<Short> = ArrayList()

    constructor(shorts: List<Short>): this() {
        this.shorts = shorts
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ShortFullscreenFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.shortList) {
            adapter = ShortFullscreenAdapter(activity as AppCompatActivity, shorts)
            PagerSnapHelper().attachToRecyclerView(this)
        }
    }
}