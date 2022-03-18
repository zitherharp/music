package com.zitherharp.music.photo.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.zitherharp.music.model.Artist
import com.zitherharp.music.photo.R
import com.zitherharp.music.photo.databinding.CategoryGridLayoutBinding

class CategoryGridFragment: Fragment() {
    private val binding: CategoryGridLayoutBinding by lazy { CategoryGridLayoutBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val context = activity as AppCompatActivity
            // Check the status of [itemFilterGroup]
            fun checkFilter() {
                when (categoryFilterGroup.checkedChipId) {
                    R.id.album_filter -> {
                        categoryRecyclerView.adapter = null
                        categoryFilterStatus.visibility = View.VISIBLE
                    }
                    R.id.artist_filter, R.id.all_filter -> {
                        categoryRecyclerView.adapter = CategoryGridAdapter(
                            context, Artist.repository.values.shuffled().subList(0, 10))
                        categoryFilterStatus.visibility = View.GONE
                    }
                }
            }
            categoryFilterGroup.setOnCheckedChangeListener { _, _ -> checkFilter() }
            categoryRefreshLayout.setOnRefreshListener {
                checkFilter()
                categoryRefreshLayout.isRefreshing = false
            }
            categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
            categoryRecyclerView.adapter = CategoryGridAdapter(context, Artist.repository.values.shuffled().subList(0, 10))
        }
    }
}