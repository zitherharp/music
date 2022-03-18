package com.zitherharp.music.shorts.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zitherharp.music.shorts.R
import com.zitherharp.music.shorts.ui.shorts.ShortGridContent

class ExploreFragment : Fragment() {

    companion object {
        fun newInstance(i: Int) = ExploreFragment()
    }

    private lateinit var viewModel: ShortGridContent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comment_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newInstance(2)
        // TODO: Use the ViewModel
    }

}