package com.zitherharp.music.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zitherharp.music.model.Video
import com.zitherharp.music.video.databinding.ActivityMainBinding
import com.zitherharp.music.video.databinding.FragmentHomeBinding
import com.zitherharp.music.video.databinding.FragmentLibraryBinding
import com.zitherharp.music.video.ui.video.VideoListAdapter

class MainActivity: AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            val navController = mainFragment.getFragment<NavHostFragment>().navController
            val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard,
                R.id.navigation_follow, R.id.navigation_library))
            navigationView.setupWithNavController(navController)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    class HomeFragment: Fragment() {
        private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            with(binding) {
                videoList.run {
                    adapter = VideoListAdapter(view.context, Video.repository.values.shuffled())
                }
            }
        }
    }

    class ExplorerFragment: Fragment() {
        private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            with(binding) {
                videoList.run {
                    adapter = VideoListAdapter(view.context, Video.repository.values.toList())
                }
            }
        }
    }

    class FollowFragment: Fragment() {
        private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            with(binding) {
                videoList.run {
                    adapter = VideoListAdapter(view.context, Video.repository.values.shuffled())
                }
            }
        }
    }

    class LibraryFragment: Fragment() {
        private val binding: FragmentLibraryBinding by lazy { FragmentLibraryBinding.inflate(layoutInflater) }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        }
    }
}