package com.zitherharp.music.photo.tv.presenter

import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Pinterest
import com.zitherharp.music.model.Music
import com.zitherharp.music.model.Photo

class PhotoPresenter: Presenter()  {
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
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val photo = item as Photo
        val image = Pinterest.Image.MEDIUM
        val cardView = viewHolder.view as ImageCardView
        cardView.titleText = photo.getName(Language.VIETNAMESE)
        cardView.contentText = photo.getName(Language.CHINESE)
        cardView.setMainImageDimensions(image.pixel, image.pixel)
        Glide.with(cardView.context)
            .load(photo.getImageUrl(image))
            .centerCrop()
            .into(cardView.mainImageView)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            badgeImage = null
            mainImage = null
        }
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) Music.getColor(Music.PHOTO) else Music.getColor(Music.AUDIO)
        view.setBackgroundColor(color)
        view.setInfoAreaBackgroundColor(color)
    }
}