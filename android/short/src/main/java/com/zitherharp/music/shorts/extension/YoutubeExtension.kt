package com.zitherharp.music.shorts.extension

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.R
import com.zitherharp.music.core.Youtube

class YoutubeExtension {

    companion object Core {
        fun Youtube.onShowImage(imageView: ImageView) {
            with(imageView) {
                setImageUrl(getImageUrl(Youtube.Image.MQDEFAULT))
                setOnClickListener {

                }
            }
        }

        fun Youtube.onShareUrl(view: View, isEmbed: Boolean = false) {
            with(view) {
                setOnClickListener {
                    context.startActivity(
                        Intent.createChooser(
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, getShareUrl(isEmbed))
                            }, context.getString(R.string.share)))
                }
            }
        }
    }
}