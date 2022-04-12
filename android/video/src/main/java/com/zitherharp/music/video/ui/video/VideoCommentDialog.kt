package com.zitherharp.music.video.ui.video

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.video.databinding.VideoCommentDialogBinding

class VideoCommentDialog : BottomSheetDialogFragment() {
    private val binding: VideoCommentDialogBinding by lazy { VideoCommentDialogBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            commentBox.run {
                requestFocus()
                doOnTextChanged { text, _, _, _ ->
                    sendComment.visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
                }
            }
            sendComment.setOnClickListener {
                dismiss()
                inputMethodManager.hideSoftInputFromWindow(commentBox.windowToken, 0)
                Toast.makeText(view.context, "Không thể bình luận", Toast.LENGTH_SHORT).show()
            }
            closeDialog.setOnClickListener {
                dismiss()
                inputMethodManager.hideSoftInputFromWindow(commentBox.windowToken, 0)
            }
        }
    }
}