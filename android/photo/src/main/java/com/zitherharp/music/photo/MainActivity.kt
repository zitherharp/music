package com.zitherharp.music.photo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zitherharp.music.photo.databinding.MainActivityBinding

class MainActivity: AppCompatActivity() {
    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        with(binding) {
            setContentView(root)
            navController = mainFragment.getFragment<NavHostFragment>().navController
            appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_main_home,
                R.id.navigation_main_explorer,
                R.id.navigation_main_search,
                R.id.navigation_main_notification,
                R.id.navigation_main_user))
            navigationView.setupWithNavController(navController)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}