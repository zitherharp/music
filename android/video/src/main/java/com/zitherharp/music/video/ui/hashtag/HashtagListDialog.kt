package com.zitherharp.music.video.ui.hashtag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getArtists
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Audio.Companion.getAudios
import com.zitherharp.music.video.databinding.HashtagListDialogBinding

class HashtagListDialog : BottomSheetDialogFragment() {
    private val binding: HashtagListDialogBinding by lazy {
        HashtagListDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val context = view.context
            arguments?.let { bundle ->
                bundle.getString(Audio::class.java.name)?.let { audioIds ->
                    videoList.adapter =
                        HashtagListAdapter.AudioListAdapter(context, audioIds.getAudios())
                }
                bundle.getString(Artist::class.java.name)?.let { artistIds ->
                    artistList.adapter =
                        HashtagListAdapter.ArtistListAdapter(context, artistIds.getArtists())
                }
            }
            closeDialog.setOnClickListener {
                dismiss()
            }
        }
    }
}