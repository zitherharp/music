package com.zitherharp.music.videotv.presenter

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Music
import com.zitherharp.music.model.Video

class VideoPresenter: Presenter()  {
    private var videoCardView: Drawable? = null
    private var sSelectedBackgroundColor = 0
    private var sDefaultBackgroundColor = 0

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val cardView = object: ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }
        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
        updateCardBackgroundColor(cardView, false)

        videoCardView = Music.getDrawable(parent.context, Music.VIDEO)
        sDefaultBackgroundColor = Music.getColor(Music.AUDIO)
        sSelectedBackgroundColor = Music.getColor(Music.VIDEO)
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val video = item as Video
        val image = Youtube.Image.HQDEFAULT
        val cardView = viewHolder.view as ImageCardView
        cardView.titleText = video.getName(Language.VIETNAMESE)
        cardView.contentText = video.getName(Language.CHINESE)
        cardView.setMainImageDimensions(image.width, image.height)
        Glide.with(cardView.context)
            .load(video.getImageUrl(image))
            .centerCrop()
            .error(videoCardView)
            .into(cardView.mainImageView)
    }

    /**
     * Remove references to images so that
     * the [Garbage Collector] can free up memory
     */
    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            badgeImage = null
            mainImage = null
        }
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) sSelectedBackgroundColor else sDefaultBackgroundColor
        view.setBackgroundColor(color)
        view.setInfoAreaBackgroundColor(color)
    }
}