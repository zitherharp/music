package com.zitherharp.music.television.ui.vertical

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zitherharp.music.television.R
import com.zitherharp.music.television.databinding.VerticalFragmentBinding

class VerticalFragment : Fragment() {
    private val binding: VerticalFragmentBinding by lazy {
        VerticalFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProvider(this)[VerticalViewModel::class.java].run {
            playlist.observe(viewLifecycleOwner) { song ->

            }
        }
    }

}