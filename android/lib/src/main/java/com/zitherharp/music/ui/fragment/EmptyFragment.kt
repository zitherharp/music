package com.zitherharp.music.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zitherharp.music.R

class EmptyFragment(): Fragment() {
    private var status: String? = null

    constructor(status: String): this() {
        this.status = status
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_empty, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.status)?.let {
            it.text = status ?: getString(R.string.empty)
        }
    }
}