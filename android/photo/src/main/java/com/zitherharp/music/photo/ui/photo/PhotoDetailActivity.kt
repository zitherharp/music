package com.zitherharp.music.photo.ui.photo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoDetailActivityBinding

class PhotoDetailActivity: AppCompatActivity() {
    private val binding: PhotoDetailActivityBinding by lazy { PhotoDetailActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        with(binding) {
            setContentView(root)
            Photo.repository[intent.getStringExtra(PhotoDetailActivity::class.simpleName)]?.let { photo ->
                // TODO: Photo
                photoImage.setImageUrl(photo.getImageUrl(Pinterest.Image.LARGE))
                photoImage.setOnClickListener {
                    startActivity(Intent(this@PhotoDetailActivity, PhotoFullscreenActivity::class.java).apply {
                        putExtra(PhotoFullscreenActivity::class.simpleName, photo.id)
                    })
                }
                photoChineseName.text = photo.getName(Language.CHINESE).ifEmpty {
                    getString(com.zitherharp.music.R.string.no_information)
                }
                photoVietnameseName.text = photo.getName(Language.VIETNAMESE).ifEmpty {
                    getString(com.zitherharp.music.R.string.no_information)
                }
                photoRecyclerView.adapter = PhotoGridAdapter(this@PhotoDetailActivity, Photo.repository.values.shuffled())
                photoRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
                photoMenuButton.setOnClickListener {
                    PhotoMenuDialog().showNow(supportFragmentManager, photo.id)
                }
                // TODO: Artist
                val artists = photo.getArtists()
                artistChineseName.text = artists.getName(Language.CHINESE)
                artistVietnameseName.text = artists.getName(Language.VIETNAMESE)
                // TODO: Other
                backButton.setOnClickListener { onBackPressed() }
            }
        }
    }
}