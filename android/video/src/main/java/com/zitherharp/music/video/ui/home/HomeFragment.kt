package com.zitherharp.music.video.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Video
import com.zitherharp.music.video.databinding.FragmentHomeBinding
import com.zitherharp.music.video.ui.video.VideoListAdapter

class HomeFragment: Fragment() {
    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            videoList.run {
                adapter = VideoListAdapter(view.context, Video.repository.values.shuffled())
            }
        }
    }
}