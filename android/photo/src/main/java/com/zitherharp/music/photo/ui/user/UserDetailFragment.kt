package com.zitherharp.music.photo.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zitherharp.music.Preference
import com.zitherharp.music.Preference.Companion.ALBUM_ID
import com.zitherharp.music.Preference.Companion.ARTIST_ID
import com.zitherharp.music.Preference.Companion.PHOTO_ID
import com.zitherharp.music.Preference.Companion.getAlbums
import com.zitherharp.music.Preference.Companion.getArtists
import com.zitherharp.music.model.Photo.Companion.getPhotos
import com.zitherharp.music.photo.databinding.UserDetailFragmentBinding
import com.zitherharp.music.photo.ui.category.CategoryGridActivity
import com.zitherharp.music.photo.ui.photo.PhotoGridAdapter

class UserDetailFragment: Fragment() {
    private val binding: UserDetailFragmentBinding by lazy { UserDetailFragmentBinding.inflate(layoutInflater) }
    private val context: AppCompatActivity by lazy { activity as AppCompatActivity }
    private val preference: Preference by lazy { Preference(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            // Photo
            photoRecyclerView.adapter = PhotoGridAdapter(context, preference.get(PHOTO_ID).getPhotos())
            photoRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
            // Artist
            artistFollowCount.text = String.format("${preference.getArtists().size} đang theo dõi")
            artistFollowView.setOnClickListener {
                startActivity(Intent(context, CategoryGridActivity::class.java).apply {
                    putExtra(CategoryGridActivity::class.java.name, ARTIST_ID)
                })
            }
            // Album
            albumFollowCount.text = String.format("${preference.getAlbums().size} đang theo dõi")
            albumFollowView.setOnClickListener {
                startActivity(Intent(context, CategoryGridActivity::class.java).apply {
                    putExtra(CategoryGridActivity::class.java.name, ALBUM_ID)
                })
            }
            // User
            userMenuButton.setOnClickListener {
                UserMenuDialog().showNow(context.supportFragmentManager, it.transitionName)
            }
            userDetailButton.setOnClickListener {
                AlertDialog.Builder(context).apply {
                    setTitle("Tài khoản được tự động tạo")
                    setMessage("Đây là tài khoản do Zither Harp Music tự động tạo cho bạn " +
                            "và chỉ có giá trị sử dụng trên thiết bị này")
                    setPositiveButton("Đã hiểu") { dlg, _ -> dlg.dismiss() }
                }.show()
            }
        }
    }
}