package com.zitherharp.music.shorts.ui.audio

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Audio
import com.zitherharp.music.shorts.databinding.AudioListContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class AudioListAdapter(private val context: Context,
                       private val audios: List<Audio>):
    RecyclerViewAdapter<AudioListContent>(context, audios) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AudioListContent(
            AudioListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AudioListContent, position: Int) {
        with(holder) {
            audios[position].run {
                itemView.setOnClickListener {
                    context.startActivity(Intent(it.context, AudioDetailActivity::class.java).apply {
                        putExtra(AudioDetailActivity::class.java.name, id)
                    })
                }
                image.setImageUrl(getImageUrl(Youtube.Image.MQDEFAULT))
                title.text = getName(Language.VIETNAMESE)
                subtitle.text = getName(Language.CHINESE)
            }
        }
    }
}