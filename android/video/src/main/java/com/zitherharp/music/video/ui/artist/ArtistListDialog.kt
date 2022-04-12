package com.zitherharp.music.video.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.model.Artist.Companion.getArtists
import com.zitherharp.music.video.databinding.ArtistListDialogBinding

class ArtistListDialog : BottomSheetDialogFragment() {
    private val binding: ArtistListDialogBinding by lazy { ArtistListDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tag?.let { artistIds ->
            with(binding) {
                artistList.adapter = ArtistListAdapter(view.context, artistIds.getArtists())
                closeDialog.setOnClickListener {
                    dismiss()
                }
            }
        }
    }
}