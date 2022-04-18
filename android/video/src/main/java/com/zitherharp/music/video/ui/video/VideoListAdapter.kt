package com.zitherharp.music.video.ui.video

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Video
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter
import com.zitherharp.music.video.VideoActivity
import com.zitherharp.music.video.databinding.VideoListContentBinding

class VideoListAdapter(private val context: Context,
                       private val videos: List<Video>):
    RecyclerViewAdapter<VideoListAdapter.VideoListContent>(context, videos) {

    class VideoListContent(binding: VideoListContentBinding): RecyclerView.ViewHolder(binding.root) {
        val videoTitle = binding.videoTitle
        val videoSubtitle = binding.videoSubtitle
        val videoDuration = binding.videoDuration
        val videoThumbnail = binding.videoThumbnail
        val artistImage = binding.artistImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoListContent(
            VideoListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: VideoListContent, position: Int) {
        with(holder) {
            // TODO: Video
            val video = videos[position]
            videoDuration.text = video.getDuration()
            videoTitle.text = video.getName(Language.VIETNAMESE)
            videoThumbnail.setImageUrl(video.getImageUrl(Youtube.Image.HQDEFAULT))
            // TODO: Artist
            val artists = video.getArtists()
            artistImage.setImageUrl(artists.first().getImageUrl(QQMusic.Image.SMALL))
            videoSubtitle.text = artists.getName(Language.VIETNAMESE, Spreadsheet.COMBINE_CHAR)
            // TODO: ItemView
            itemView.setOnClickListener {
                context.startActivity(Intent(context, VideoActivity::class.java).apply {
                    putExtra(VideoActivity.VIDEO_ID, video.id)
                })
            }
        }
    }
}