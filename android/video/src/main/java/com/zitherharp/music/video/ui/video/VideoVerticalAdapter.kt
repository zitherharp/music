package com.zitherharp.music.video.ui.video

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.*
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter
import com.zitherharp.music.video.VideoActivity
import com.zitherharp.music.video.databinding.VideoVerticalContentBinding

class VideoVerticalAdapter(private val context: Context,
                           private val videos: List<Video>):
    RecyclerViewAdapter<VideoVerticalAdapter.VideoVerticalContent>(context, videos) {

    class VideoVerticalContent(binding: VideoVerticalContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val videoTitle = binding.videoTitle
        val videoSubtitle = binding.videoSubtitle
        val videoDuration = binding.videoDuration
        val videoThumbnail = binding.videoThumbnail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoVerticalContent(
            VideoVerticalContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: VideoVerticalContent, position: Int) {
        with(holder) {
            val video = videos[position]
            videoTitle.text = video.getName(Language.VIETNAMESE)
            videoSubtitle.text = video.getArtists().getName(Language.VIETNAMESE)
            videoDuration.text = video.getDuration()
            videoThumbnail.setImageUrl(video.getImageUrl(Youtube.Image.MQDEFAULT))
            itemView.setOnClickListener {
                context.startActivity(Intent(context, VideoActivity::class.java).apply {
                    putExtra(VideoActivity.VIDEO_ID, video.id)
                })
            }
        }
    }
}