package com.zitherharp.music.photo.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoDetailContentBinding
import com.zitherharp.music.photo.ui.photo.*
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class PhotoDetailAdapter(private val activity: PhotoDetailActivity,
                         private val photos: List<Photo>):
    RecyclerViewAdapter<PhotoDetailContent>(activity, photos) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoDetailContent(
            PhotoDetailContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PhotoDetailContent, position: Int) {
        with(holder) {
            // TODO: Photo
            val photo = photos[position]
            photoImage.setImageUrl(photo.getImageUrl(Pinterest.Image.LARGE))
            photoImage.setOnClickListener {
                activity.startActivity(Intent(activity, PhotoFullscreenActivity::class.java).apply {
                    putExtra(PhotoFullscreenActivity::class.simpleName, photos.getId())
                    putExtra(PhotoFullscreenActivity::class.qualifiedName, position)
                })
            }
            photoChineseName.text = photo.getName(Language.CHINESE).ifEmpty {
                activity.getString(com.zitherharp.music.R.string.no_information)
            }
            photoVietnameseName.text = photo.getName(Language.VIETNAMESE).ifEmpty {
                activity.getString(com.zitherharp.music.R.string.no_information)
            }

            photoRecyclerView.adapter = PhotoGridAdapter(activity, Photo.repository.values.shuffled())
            photoRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
            photoMenuButton.setOnClickListener {
                PhotoMenuDialog().showNow(activity.supportFragmentManager, photo.id)
            }
            // TODO: Artist
            val artists = photo.getArtists()
            artistImage.setImageUrl(artists.first().getImageUrl(QQMusic.Image.SMALL))
            artistChineseName.text = artists.getName(Language.CHINESE)
            artistVietnameseName.text = artists.getName(Language.VIETNAMESE)
            // TODO: Other
            backButton.setOnClickListener { activity.onBackPressed() }
        }
    }
}