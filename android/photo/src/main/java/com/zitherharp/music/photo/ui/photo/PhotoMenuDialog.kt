package com.zitherharp.music.photo.ui.photo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.Preference
import com.zitherharp.music.core.Pinterest
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoMenuDialogBinding

class PhotoMenuDialog: BottomSheetDialogFragment() {
    private val binding: PhotoMenuDialogBinding by lazy { PhotoMenuDialogBinding.inflate(layoutInflater) }
    private val preference: Preference by lazy { Preference(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            Photo.repository[tag]?.let { photo ->
                // Set view
                if (!preference.contains(Preference.PHOTO_ID, photo.id)) {
                    followView.visibility = View.VISIBLE
                    unfollowView.visibility = View.GONE
                } else {
                    followView.visibility = View.GONE
                    unfollowView.visibility = View.VISIBLE
                }
                // Follow
                followView.setOnClickListener {
                    dismiss()
                    followView.visibility = View.GONE
                    unfollowView.visibility = View.VISIBLE
                    preference.add(Preference.PHOTO_ID, photo.id)
                    Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show()
                }
                unfollowView.setOnClickListener {
                    dismiss()
                    followView.visibility = View.VISIBLE
                    unfollowView.visibility = View.GONE
                    preference.remove(Preference.PHOTO_ID, photo.id)
                    Toast.makeText(context, "Đã xoá khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show()
                }
                // Download
                downloadView.setOnClickListener {

                }
                // Share
                shareView.setOnClickListener {
                    startActivity(Intent.createChooser(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, photo.getImageUrl(Pinterest.Image.ORIGINALS))
                        }, getString(com.zitherharp.music.R.string.share)))
                }
            }
        }
    }
}