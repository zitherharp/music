package com.zitherharp.music.shorts.ui.shorts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortGridContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class ShortGridAdapter(private val context: Context,
                       private val shorts: List<Short>):
    RecyclerViewAdapter<ShortGridContent>(context, shorts) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShortGridContent(
            ShortGridContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ShortGridContent, position: Int) {
        with(holder) {
            itemView.setOnClickListener {
                context.startActivity(Intent(context, ShortFullscreenActivity::class.java).apply {
                    putExtra(ShortFullscreenActivity::class.simpleName, position)
                    putExtra(ShortFullscreenActivity::class.qualifiedName, shorts.getId())
                })
            }
            shorts[position].run {
                image.setImageUrl(getImageUrl(Youtube.Image.HQDEFAULT))
            }
        }
    }
}