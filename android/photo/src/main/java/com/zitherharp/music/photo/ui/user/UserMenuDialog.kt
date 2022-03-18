package com.zitherharp.music.photo.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.photo.databinding.UserMenuDialogBinding

class UserMenuDialog: BottomSheetDialogFragment() {
    private val binding: UserMenuDialogBinding by lazy { UserMenuDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

        }
    }
}