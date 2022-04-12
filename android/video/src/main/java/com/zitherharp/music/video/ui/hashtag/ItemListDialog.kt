package com.zitherharp.music.video.ui.hashtag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.model.Artist.Companion.getArtists
import com.zitherharp.music.model.Video.Companion.getVideos
import com.zitherharp.music.video.ArtistActivity
import com.zitherharp.music.video.VideoActivity
import com.zitherharp.music.video.databinding.ItemListDialogBinding
import com.zitherharp.music.video.ui.artist.ArtistListAdapter
import com.zitherharp.music.video.ui.video.VideoListAdapter

class ItemListDialog : BottomSheetDialogFragment() {
    private val binding: ItemListDialogBinding by lazy { ItemListDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val context = view.context
            arguments?.let { bundle ->
                bundle.getString(VideoActivity::class.java.name)?.let { videoIds ->
                    itemList1.adapter = VideoListAdapter(context, videoIds.getVideos())
                }
                bundle.getString(ArtistActivity::class.java.name)?.let { artistIds ->
                    itemList2.adapter = Item.ArtistListAdapter(context, artistIds.getArtists())
                }
            }
            closeDialog.setOnClickListener {
                dismiss()
            }
        }
    }
}