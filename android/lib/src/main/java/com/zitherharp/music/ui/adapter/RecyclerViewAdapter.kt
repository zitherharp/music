package com.zitherharp.music.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.core.Spreadsheet
import java.util.*

abstract class RecyclerViewAdapter<VH>(private val context: Context,
                                       private val items: List<Any>):
    RecyclerView.Adapter<VH>() where VH: RecyclerView.ViewHolder {

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = items.size
}