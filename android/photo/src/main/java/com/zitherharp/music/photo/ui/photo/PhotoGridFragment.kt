package com.zitherharp.music.photo.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoGridLayoutBinding

class PhotoGridFragment: Fragment() {
    private val binding: PhotoGridLayoutBinding by lazy { PhotoGridLayoutBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val context = activity as AppCompatActivity
            photoRefreshLayout.setOnRefreshListener {
                photoRefreshLayout.isRefreshing = false
            }
            photoRecyclerView.adapter = PhotoGridAdapter(context, Photo.repository.values.shuffled())
            photoRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        }
    }
}