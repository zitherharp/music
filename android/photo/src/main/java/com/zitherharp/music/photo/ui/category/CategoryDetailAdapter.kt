package com.zitherharp.music.photo.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.zitherharp.music.photo.databinding.ArtistDetailActivityBinding

class CategoryDetailAdapter: AppCompatActivity() {
    private val binding: ArtistDetailActivityBinding by lazy { ArtistDetailActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
        }
    }
}