package com.zitherharp.music.photo.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.photo.databinding.PhotoCategoryContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class CategoryListAdapter(private val context: Context,
                          private val categories: List<QQMusic>) :
    RecyclerViewAdapter<CategoryListAdapter.PhotoCategoryContent>(context, categories) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoCategoryContent(
            PhotoCategoryContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PhotoCategoryContent, position: Int) {
        with(holder) {
            val category = categories[position]
            categoryImage.setImageUrl(category.getImageUrl(QQMusic.Image.SMALL))
            categoryChineseName.text = category.getName(Language.CHINESE)
            categoryVietnameseName.text = category.getName(Language.VIETNAMESE)
        }
    }

    inner class PhotoCategoryContent(binding: PhotoCategoryContentBinding): RecyclerView.ViewHolder(binding.root) {
        val categoryImage = binding.categoryImage
        val categoryChineseName = binding.categoryChineseName
        val categoryVietnameseName = binding.categoryVietnameseName
    }
}