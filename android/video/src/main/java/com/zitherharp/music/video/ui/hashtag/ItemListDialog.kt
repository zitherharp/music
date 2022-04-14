package com.zitherharp.music.video.ui.hashtag

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getArtists
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Audio.Companion.getAudios
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter
import com.zitherharp.music.video.ArtistActivity
import com.zitherharp.music.video.HashtagActivity
import com.zitherharp.music.video.VideoActivity
import com.zitherharp.music.video.databinding.ItemListContentBinding
import com.zitherharp.music.video.databinding.ItemListDialogBinding

class ItemListDialog : BottomSheetDialogFragment() {
    private val binding: ItemListDialogBinding by lazy { ItemListDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val context = view.context
            tag?.let { header ->
                itemHeader.text = header
                arguments?.let { bundle ->
                    val hasHashtag = tag == getString(com.zitherharp.music.R.string.tag)
                    bundle.getString(VideoActivity::class.java.name)?.let { audioIds ->
                        videoList.adapter = AudioListAdapter(context, audioIds.getAudios())
                    }
                    bundle.getString(ArtistActivity::class.java.name)?.let { artistIds ->
                        artistList.adapter = ArtistListAdapter(context, artistIds.getArtists(), hasHashtag)
                    }
                }
            }
            closeDialog.setOnClickListener {
                dismiss()
            }
        }
    }

    abstract class ItemListAdapter(context: Context, items: List<Spreadsheet>) :
        RecyclerViewAdapter<ItemListAdapter.ItemListContent>(context, items) {

        class ItemListContent(binding: ItemListContentBinding) : RecyclerView.ViewHolder(binding.root) {
            val itemImage = binding.itemImage
            val itemTitle = binding.itemTitle
            val itemSubtitle = binding.itemSubtitle
            //val itemOption = binding.itemOption
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemListContent(
                ItemListContentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    class HashtagListAdapter(private val context: Context,
                             private val audios: List<Audio>) : ItemListAdapter(context, audios) {

        override fun onBindViewHolder(holder: ItemListContent, position: Int) {
            with(holder) {
                val audio = audios[position]
                itemImage.setImageUrl(audio.getImageUrl(Youtube.Image.DEFAULT))
                itemTitle.text = audio.getName(Language.VIETNAMESE)
                itemSubtitle.text = String.format("${audio.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, HashtagActivity::class.java).apply {
                        putExtra(HashtagActivity::class.java.name, audio.id)
                    })
                }
            }
        }
    }

    class AudioListAdapter(private val context: Context,
                           private val audios: List<Audio>) : ItemListAdapter(context, audios) {

        override fun onBindViewHolder(holder: ItemListContent, position: Int) {
            with(holder) {
                val audio = audios[position]
                itemImage.setImageUrl(audio.getImageUrl(Youtube.Image.DEFAULT))
                itemTitle.text = audio.getName(Language.VIETNAMESE)
                itemSubtitle.text = String.format("${audio.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, ArtistActivity::class.java).apply {
                        putExtra(ArtistActivity::class.java.name, audio.id)
                    })
                }
            }
        }
    }

    class ArtistListAdapter(private val context: Context,
                            private val artists: List<Artist>,
                            private val hasHashtag: Boolean = false) : ItemListAdapter(context, artists) {

        override fun onBindViewHolder(holder: ItemListContent, position: Int) {
            with(holder) {
                val artist = artists[position]
                itemImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
                itemTitle.text = if (!hasHashtag)
                    artist.getName(Language.VIETNAMESE) else "#${artist.getName(Language.CHINESE)}"
                itemSubtitle.text = String.format("${artist.getVideos().size} video")
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, ArtistActivity::class.java).apply {
                        putExtra(ArtistActivity::class.java.name, artist.id)
                    })
                }
            }
        }
    }
}