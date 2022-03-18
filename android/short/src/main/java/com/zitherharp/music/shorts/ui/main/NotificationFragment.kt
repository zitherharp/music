package com.zitherharp.music.shorts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.shorts.MainActivity
import com.zitherharp.music.R
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.NotificationMainFragmentBinding
import com.zitherharp.music.shorts.model.User
import com.zitherharp.music.shorts.ui.artist.ArtistListFragment
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class NotificationFragment: Fragment() {
    private lateinit var binding: NotificationMainFragmentBinding
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        NotificationMainFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
            user = (activity as MainActivity).currentUser
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            NotificationMainAdapter(this@NotificationFragment,
                arrayOf(
                    getString(R.string.audio),
                    getString(R.string.shorts),
                    getString(R.string.artist)
                ))
                .attach(notificationTabLayout, notificationViewPager, 1)
        }
    }

    inner class NotificationMainAdapter(fragment: NotificationFragment,
                                        tabNames: Array<String>): FragmentStateAdapter(fragment, tabNames) {
        private val audioFragment = AudioListFragment(Audio.repository.values
            .sortedByDescending { audio -> audio.getShorts().size }.subList(0, 15))
        private val shortFragment = ShortGridFragment(Short.repository.values.shuffled().subList(0, 15))
        private val artistFragment = ArtistListFragment(Artist.repository.values
            .sortedByDescending { artist -> artist.getShorts().size }.subList(0, 15))

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return audioFragment
                1 -> return shortFragment
                2 -> return artistFragment
            }
            return super.createFragment(position)
        }
    }
}