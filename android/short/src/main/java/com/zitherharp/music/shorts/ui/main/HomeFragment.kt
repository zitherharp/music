package com.zitherharp.music.shorts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getShorts
import com.zitherharp.music.shorts.databinding.HomeMainFragmentBinding
import com.zitherharp.music.shorts.model.User
import com.zitherharp.music.shorts.ui.shorts.ShortFullscreenFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class HomeFragment: Fragment() {
    private lateinit var binding: HomeMainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        HomeMainFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            HomeAdapter(this@HomeFragment,
                arrayOf("Theo dõi", "Đề xuất"))
                .attach(tabLayout, viewPager, 1)
        }
    }

    inner class HomeAdapter(fragment: HomeFragment,
                            tabNames: Array<String>): FragmentStateAdapter(fragment, tabNames) {
        private val followShorts = User(fragment.requireContext()).artistId.getShorts()
        private val followFragment = if (followShorts.isNotEmpty())
            ShortFullscreenFragment(followShorts) else EmptyFragment()
        private val recommendShorts = Short.repository.values.shuffled().subList(0, 50)
        private val recommendFragment = ShortFullscreenFragment(recommendShorts)

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return followFragment
                1 -> return recommendFragment
            }
            return super.createFragment(position)
        }
    }
}