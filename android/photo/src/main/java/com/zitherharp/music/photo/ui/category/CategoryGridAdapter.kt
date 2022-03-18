package com.zitherharp.music.photo.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.photo.databinding.CategoryGridContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class CategoryGridAdapter(private val context: Context,
                          private val categories: List<QQMusic>) :
    RecyclerViewAdapter<CategoryGridAdapter.CategoryGridContent>(context, categories) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryGridContent(
            CategoryGridContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CategoryGridContent, position: Int) {
        with(holder) {
            val category = categories[position]
            categoryImage.setImageUrl(category.getImageUrl(QQMusic.Image.SMALL))
            categoryChineseName.text = category.getName(Language.CHINESE)
            categoryVietnameseName.text = category.getName(Language.VIETNAMESE)
        }
    }

    inner class CategoryGridContent(binding: CategoryGridContentBinding): RecyclerView.ViewHolder(binding.root) {
        val categoryImage = binding.artistImage
        val categoryChineseName = binding.artistChineseName
        val categoryVietnameseName = binding.artistVietnameseName
    }
}