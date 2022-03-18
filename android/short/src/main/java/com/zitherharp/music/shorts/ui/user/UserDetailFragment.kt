package com.zitherharp.music.shorts.ui.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getArtists
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Audio.Companion.getAudios
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getShorts
import com.zitherharp.music.R
import com.zitherharp.music.shorts.MainActivity
import com.zitherharp.music.shorts.databinding.UserDetailFragmentBinding
import com.zitherharp.music.shorts.extension.Extension.copyToClipboard
import com.zitherharp.music.shorts.model.User
import com.zitherharp.music.shorts.ui.artist.ArtistDetailActivity
import com.zitherharp.music.shorts.ui.artist.ArtistListFragment
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class UserDetailFragment: Fragment() {
    private lateinit var binding: UserDetailFragmentBinding
    private lateinit var user: User
    private lateinit var shorts: List<Short>
    private lateinit var audios: List<Audio>
    private lateinit var artists: List<Artist>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        UserDetailFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        user = (activity as MainActivity).currentUser
        with(binding) {
            with(user) {
                shorts = shortId.getShorts()
                audios = audioId.getAudios()
                artists = artistId.getArtists()
                with(userId) {
                    text = String.format("ID: ${user.id}")
                    setOnClickListener { copyToClipboard() }
                }
                with (userName) {
                    text = name
                    setOnClickListener {
                        val editText = EditText(context).apply {
                            hint = user.artistId
                        }
                        AlertDialog.Builder(context).apply {
                            setView(editText)
                            setTitle("Đổi tên người dùng")
                            setNegativeButton(getString(R.string.cancel)) { dlg, _ -> dlg.cancel() }
                            setPositiveButton(getString(R.string.save)) { _, _ ->
                                run {
                                    val newName = editText.text.toString()
                                    if (newName.isNotBlank()) {
                                        edit(User.NAME, newName)
                                        userName.text = newName
                                        Toast.makeText(context, "Đổi tên thành công", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Tên không hợp lệ", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }.show()
                    }
                }
            }
            UserDetailAdapter(this@UserDetailFragment,
                arrayOf("${getString(R.string.audio)} ${audios.size}",
                    "${getString(R.string.shorts)} ${shorts.size}",
                    "${getString(R.string.artist)} ${artists.size}"))
                .attach(userTabLayout, userViewPager, 1)
        }
    }

    inner class UserDetailAdapter(fragment: UserDetailFragment,
                                  tabNames: Array<String>): FragmentStateAdapter(fragment, tabNames) {
        private val audioFragment = if (audios.isNotEmpty()) AudioListFragment(audios) else EmptyFragment()
        private val shortFragment = if (shorts.isNotEmpty()) ShortGridFragment(shorts) else EmptyFragment()
        private val artistFragment = if (artists.isNotEmpty()) ArtistListFragment(artists) else EmptyFragment()

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