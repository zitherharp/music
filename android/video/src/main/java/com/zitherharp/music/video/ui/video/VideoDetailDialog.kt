package com.zitherharp.music.video.ui.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Video
import com.zitherharp.music.video.ArtistActivity
import com.zitherharp.music.video.databinding.VideoDescriptionDialogBinding
import com.zitherharp.music.video.ui.artist.ArtistListDialog

class VideoDetailDialog : BottomSheetDialogFragment() {
    private val binding: VideoDescriptionDialogBinding by lazy { VideoDescriptionDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Video.repository[tag]?.let { video ->
            with(binding) {
                // TODO: Artist
                val artists = video.getArtists()
                val artist = artists.first()
                artistTitle.text = artists.getName(Language.VIETNAMESE, Spreadsheet.COMBINE_CHAR)
                artistImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
                artistLayout.setOnClickListener {
                    if (artists.size == 1) {
                        startActivity(Intent(context, ArtistActivity::class.java).apply {
                            putExtra(ArtistActivity::class.java.name, artist.id)
                        })
                    } else {
                        ArtistListDialog().apply {
                            isCancelable = false
                        }.showNow(requireActivity().supportFragmentManager, artists.getId())
                    }
                }
                // TODO: Video
                videoTitle.text = video.getName(Language.VIETNAMESE)
                videoDescription.text = video.getHashTag(Language.CHINESE).ifEmpty {
                    getString(com.zitherharp.music.R.string.no_information)
                }
                // TODO: Others
                audioName.text = videoTitle.text
                albumName.text = videoTitle.text
                artistName.text = artistTitle.text
                closeDialog.setOnClickListener {
                    dismiss()
                }
            }
        }
    }
}