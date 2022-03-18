package com.zitherharp.music.shorts.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.shorts.databinding.CommentListFragmentBinding

class CommentListDialog: BottomSheetDialogFragment() {
    private lateinit var binding: CommentListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        CommentListFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            with(commentBar) {
                sendComment.setOnClickListener {
                    dismiss()
                    commentBox.setText(Spreadsheet.EMPTY_CHAR)
                    Toast.makeText(it.context, "Bình luận thành công", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}