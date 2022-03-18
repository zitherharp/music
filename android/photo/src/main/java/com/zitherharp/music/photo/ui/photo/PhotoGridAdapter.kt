package com.zitherharp.music.photo.ui.photo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Pinterest
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoGridContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class PhotoGridAdapter(private val activity: AppCompatActivity,
                       private val photos: List<Photo>):
    RecyclerViewAdapter<PhotoGridAdapter.PhotoGridContent>(activity, photos) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoGridContent(
            PhotoGridContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PhotoGridContent, position: Int) {
        with(holder) {
            val photo = photos[position]
            fun onVisible() {
                itemView.run {
                    setOnClickListener {
                        context.startActivity(Intent(context, PhotoDetailActivity::class.java).apply {
                            putExtra(PhotoDetailActivity::class.simpleName, photo.id)
                        })
                    }
                    setOnLongClickListener {
                        PhotoMenuDialog().showNow(activity.supportFragmentManager, photo.id)
                        it.isEnabled
                    }
                }
                photoImage.visibility = View.VISIBLE
                photoVisibilityButton.setImageResource(com.zitherharp.music.R.drawable.ic_invisible)
            }
            fun onInvisible() {
                itemView.setOnClickListener(null)
                itemView.setOnLongClickListener(null)
                photoImage.visibility = View.GONE
                photoVisibilityButton.setImageResource(com.zitherharp.music.R.drawable.ic_visible)
            }
            onVisible()
            artistImage.setImageUrl(photo.getArtists().first().getImageUrl(QQMusic.Image.SMALL))
            photoImage.setImageUrl(photo.getImageUrl(Pinterest.Image.SMALL))
            photoVisibilityButton.setOnClickListener {
                if (photoImage.visibility == View.VISIBLE) {
                    onInvisible()
                    Snackbar.make(it, "Đã ẩn", Snackbar.LENGTH_SHORT).setAction(
                        activity.getString(com.zitherharp.music.R.string.undo)) { onVisible() }.show()
                } else {
                    onVisible()
                }
            }
        }
    }

    inner class PhotoGridContent(binding: PhotoGridContentBinding): RecyclerView.ViewHolder(binding.root) {
        val artistImage = binding.artistImage
        val photoImage = binding.photoImage
        val photoVisibilityButton = binding.photoVisibilityButton
    }
}
