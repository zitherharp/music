package com.zitherharp.music.shorts.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Artist
import com.zitherharp.music.shorts.databinding.ArtistListFragmentBinding

class ArtistListFragment(): Fragment() {
    private lateinit var binding: ArtistListFragmentBinding
    private var artists: List<Artist> = ArrayList()

    constructor(artists: List<Artist>): this() {
        this.artists = artists
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ArtistListFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.artistList) {
            adapter = ArtistListAdapter(view.context, artists)
        }
    }
}