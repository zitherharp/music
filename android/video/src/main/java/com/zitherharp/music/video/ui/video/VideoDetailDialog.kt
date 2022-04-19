package com.zitherharp.music.video.ui.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Video
import com.zitherharp.music.video.ArtistActivity
import com.zitherharp.music.video.HashtagActivity.Companion.getHashtag
import com.zitherharp.music.video.databinding.VideoDetailDialogBinding
import com.zitherharp.music.video.ui.artist.ArtistListDialog
import com.zitherharp.music.video.ui.hashtag.HashtagListDialog
import kotlin.random.Random

class VideoDetailDialog : BottomSheetDialogFragment() {
    private val binding: VideoDetailDialogBinding by lazy { VideoDetailDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Video.repository[tag]?.let { video ->
            with(binding) {
                // TODO: Artist
                val artists = video.getArtists()
                val artist = artists.first()
                artistTitle.text = artists.getName(Language.VIETNAMESE)
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
                videoDescription.text = video.getHashtag(Language.CHINESE).ifEmpty {
                    getString(com.zitherharp.music.R.string.no_information)
                }
                videoHashtag.run {
                    text = video.getHashtag(Language.CHINESE)
                    setOnClickListener {
                        HashtagListDialog().apply {
                            isCancelable = false
                            arguments = Bundle().apply {
                                putString(Audio::class.java.name, video.getAudios().getId())
                                putString(Artist::class.java.name, video.getArtists().getId())
                            }
                        }.showNow(requireActivity().supportFragmentManager, getString(com.zitherharp.music.R.string.tag))
                    }
                }
                likeCount.text = String.format("${Random.nextInt(1, 10)}")
                viewCount.text = String.format("${Random.nextInt(1, 1000)}")
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