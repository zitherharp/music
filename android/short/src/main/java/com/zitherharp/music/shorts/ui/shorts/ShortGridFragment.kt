package com.zitherharp.music.shorts.ui.shorts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.zitherharp.music.R
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortGridFragmentBinding

class ShortGridFragment(): Fragment() {
    private lateinit var binding: ShortGridFragmentBinding
    private var shorts: List<Short> = ArrayList()

    constructor(shorts: List<Short>): this() {
        this.shorts = shorts
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ShortGridFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            shortGrid.adapter = ShortGridAdapter(view.context, shorts)
            shortGrid.layoutManager = GridLayoutManager(context, 3)
        }
    }
}