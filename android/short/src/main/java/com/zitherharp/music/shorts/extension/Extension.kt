package com.zitherharp.music.shorts.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.shorts.R
import com.zitherharp.music.shorts.extension.Extension.onChineseName
import com.zitherharp.music.shorts.model.User
import com.zitherharp.music.shorts.ui.artist.ArtistDetailActivity
import com.zitherharp.music.shorts.ui.artist.ArtistListDialog
import com.zitherharp.music.shorts.ui.audio.AudioDetailActivity
import com.zitherharp.music.shorts.ui.audio.AudioListDialog

object Extension {
    private fun View.onImage(url: String) {
       AlertDialog.Builder(context).apply {
            setView(ImageView(context).apply {
                setImageUrl(url)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            })
       }.show()
    }

    fun ImageView.onImage(item: QQMusic) {
        setImageUrl(item.getImageUrl(QQMusic.Image.MEDIUM))
        setOnClickListener {
            it.onImage(item.getImageUrl(QQMusic.Image.LARGE))
        }
    }

    fun ImageView.onImage(item: Youtube) {
        setImageUrl(item.getImageUrl(Youtube.Image.SDDEFAULT))
        setOnClickListener {
            it.onImage(item.getImageUrl(Youtube.Image.SDDEFAULT))
        }
    }

    fun TextView.copyToClipboard() {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
            setPrimaryClip(
                ClipData.newPlainText(context.packageName,
                    this@copyToClipboard.text.toString()
                ))
        }
        Toast.makeText(context, "Đã sao chép", Toast.LENGTH_SHORT).show()
    }

    fun TextView.onChineseName(item: Spreadsheet) {
        text = item.getName(Language.CHINESE)
        setOnClickListener { copyToClipboard() }
    }

    fun TextView.onDescription(item: Spreadsheet) {
        setOnClickListener {
            AlertDialog.Builder(context).apply {
                val description = item.getDescription(Language.VIETNAMESE)
                setTitle(item.getName(Language.VIETNAMESE))
                if (description.isNotEmpty()) {
                    setMessage(description)
                } else {
                    setMessage("Không có mô tả")
                }
                setPositiveButton(context.getString(com.zitherharp.music.R.string.close)) { dlg, _ -> dlg.dismiss() }
            }.show()
        }
    }

    fun ImageButton.onFavourite(key: String, value: String) {
        val user = User(context)
        if (!user.contains(key, value)) {
            setImageResource(R.drawable.ic_short_favorite_border_24)
        } else {
            setImageResource(R.drawable.ic_short_favorite_24)
        }
        setOnClickListener {
            if (!user.contains(key, value)) {
                user.add(key, value)
                setImageResource(R.drawable.ic_short_favorite_24)
                Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(context).apply {
                    setMessage("Xác nhận xoá khỏi danh sách yêu thích?")
                    setNegativeButton("Huỷ") { dlg, _ -> dlg.dismiss() }
                    setPositiveButton("OK") { _, _ ->
                        user.remove(key, value)
                        setImageResource(R.drawable.ic_short_favorite_border_24)
                        Toast.makeText(context, "Đã xoá thành công", Toast.LENGTH_SHORT).show()
                    }
                }.show()
            }
        }
    }

    fun ImageButton.onShare(item: Youtube) {
        setOnClickListener {
            context.startActivity(Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, item.getShareUrl())
                }, context.getString(com.zitherharp.music.R.string.share)))
        }
    }

    fun View.onArtistDetailActivity(fragmentManager: FragmentManager, artists: List<Artist>) {
        setOnClickListener {
            if (artists.size == 1) {
                context.startActivity(
                    Intent(context, ArtistDetailActivity::class.java).apply {
                        putExtra(ArtistDetailActivity::class.java.name, artists.first().id)
                    })
            } else if (artists.size > 1) {
                ArtistListDialog(artists).showNow(fragmentManager, ArtistListDialog::class.java.name)
            }
        }
    }

    fun View.onAudioDetailActivity(fragmentManager: FragmentManager, audios: List<Audio>) {
        setOnClickListener {
            if (audios.size == 1) {
                context.startActivity(
                    Intent(context, AudioDetailActivity::class.java).apply {
                        putExtra(AudioDetailActivity::class.java.name, audios.first().id)
                    })
            } else if (audios.size > 1) {
                AudioListDialog(audios).showNow(fragmentManager, AudioListDialog::class.java.name)
            }
        }
    }
}