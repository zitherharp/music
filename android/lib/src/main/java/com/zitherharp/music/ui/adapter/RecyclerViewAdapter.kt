package com.zitherharp.music.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.core.Spreadsheet

abstract class RecyclerViewAdapter<VH>(private val context: Context,
                                       private val items: List<Spreadsheet>):
    RecyclerView.Adapter<VH>() where VH: RecyclerView.ViewHolder {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = items.size
}