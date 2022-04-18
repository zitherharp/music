package com.zitherharp.music.photo.ui.category

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zitherharp.music.Preference
import com.zitherharp.music.model.Album.Companion.getAlbums
import com.zitherharp.music.model.Artist.Companion.getArtists
import com.zitherharp.music.photo.R
import com.zitherharp.music.photo.databinding.CategoryFollowingActivityBinding
import com.zitherharp.music.photo.databinding.CategoryGridLayoutBinding
import com.zitherharp.music.photo.ui.artist.ArtistGridAdapter

class CategoryGridActivity: AppCompatActivity() {
    private val binding: CategoryGridLayoutBinding by lazy { CategoryGridLayoutBinding.inflate(layoutInflater) }
    private val preference: Preference by lazy { Preference(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(com.zitherharp.music.R.string.following)
        with (binding) {
            setContentView(root)
            val albums = preference.get(Preference.ALBUM_ID).getAlbums()
            val artists = preference.get(Preference.ARTIST_ID)!!.getArtists()
            fun checkFilter(filterId: String?) {
                when (filterId) {
                    Preference.ALBUM_ID, R.id.album_filter.toString() -> {
                        categoryFilterGroup.check(R.id.album_filter)
                        if (albums.isNotEmpty()) {
                            categoryFilterStatus.visibility = View.GONE
                            //itemRecyclerView.adapter = ArtistGridAdapter(this@PhotoCategoryActivity, albums)
                        } else {
                            categoryFilterStatus.visibility = View.VISIBLE
                            categoryRecyclerView.adapter = null
                        }
                    }
                    Preference.ARTIST_ID, R.id.artist_filter.toString() -> {
                        categoryFilterGroup.check(R.id.artist_filter)
                        if (artists.isNotEmpty()) {
                            categoryFilterStatus.visibility = View.GONE
                            categoryRecyclerView.adapter = CategoryGridAdapter(this@CategoryGridActivity, artists)
                        } else {
                            categoryFilterStatus.visibility = View.VISIBLE
                            categoryRecyclerView.adapter = null
                        }
                    }
                }
            }
            // onLoad()
            checkFilter(intent.getStringExtra(CategoryGridActivity::class.java.name))
            categoryFilterGroup.setOnCheckedChangeListener { _, _ ->
                checkFilter(categoryFilterGroup.checkedChipId.toString())
            }
            categoryRefreshLayout.setOnRefreshListener {
                categoryRefreshLayout.isRefreshing = false
                checkFilter(categoryFilterGroup.checkedChipId.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}