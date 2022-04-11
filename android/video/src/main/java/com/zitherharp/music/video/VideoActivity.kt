package com.zitherharp.music.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Extension.share
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Video
import com.zitherharp.music.video.databinding.*
import com.zitherharp.music.video.ui.video.VideoListAdapter

class VideoActivity : AppCompatActivity() {
    private val binding: ActivityVideoBinding by lazy { ActivityVideoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Video.repository[intent.getStringExtra(VideoActivity::class.java.name)]?.let { video ->
            with(binding) {
                setContentView(root)
                val context = this@VideoActivity.apply {
                    lifecycle.addObserver(videoPlayerView)
                }
                videoPlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadOrCueVideo(context.lifecycle, video.id, 0F)
                    }
                })
                // TODO: ArtistLayout
                val artists = video.getArtists()
                artistTitle.text = artists.getName(Language.VIETNAMESE, Spreadsheet.COMBINE_CHAR)
                artistImage.setImageUrl(artists.first().getImageUrl(QQMusic.Image.SMALL))
                artistLayout.setOnClickListener {
                    startActivity(Intent(context, ArtistActivity::class.java).apply {
                        putExtra(ArtistActivity::class.java.name, artists.toString())
                    })
                }
                // TODO: VideoLayout
                videoTitle.text = video.getName(Language.VIETNAMESE)
                videoSubtitle.text = artists.getName(Language.VIETNAMESE)
                video.getHashTag().split(Spreadsheet.SPACE_CHAR).run {
                    videoHashTag1.text = this[0]
                    videoHashTag2.text = this[1]
                    if (size > 2) {
                        videoHashTag3.text = this[2]
                    }
                }
                // TODO: ButtonBar
                shareVideo.setOnClickListener { share(video.getShareUrl()) }
                downloadVideo.setOnClickListener {
                    Snackbar.make(it, "Không thể tải xuống video này", Snackbar.LENGTH_SHORT).show()
                }
                // TODO: DescriptionDialog
                videoDescriptionLayout.setOnClickListener {
                    DescriptionDialog().apply {
                        isCancelable = false
                    }.showNow(supportFragmentManager, video.id)
                }
                // TODO: CommentDialog
                fun showCommentDialog(showKeyboard: Boolean) {
                    CommentDialog().apply {
                        isCancelable = false
                        arguments = Bundle().apply {
                            putBoolean("showKeyboard", showKeyboard)
                        }
                    }.showNow(supportFragmentManager, video.id)
                }
                videoCommentLayout.setOnClickListener {
                    showCommentDialog(showKeyboard = false)
                }
                videoCommentBox.setOnClickListener {
                    showCommentDialog(showKeyboard = true)
                }
                // TODO: VideoList
                with(videoListLayout) {
                    videoList.adapter = VideoListAdapter(context, Video.repository.values.shuffled().take(10))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoPlayerView.run {
            release()
            lifecycle.removeObserver(this)
        }
    }

    class DescriptionDialog : BottomSheetDialogFragment() {
        private val binding: VideoDescriptionDialogBinding by lazy { VideoDescriptionDialogBinding.inflate(layoutInflater) }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            Video.repository[tag]?.let { video ->
                with(binding) {
                    val artists = video.getArtists()
                    artistTitle.text = artists.getName(Language.VIETNAMESE, Spreadsheet.COMBINE_CHAR)
                    artistImage.setImageUrl(artists.first().getImageUrl(QQMusic.Image.SMALL))
                    videoTitle.text = video.getName(Language.VIETNAMESE)
                    videoDescription.text = video.getHashTag().ifEmpty {
                        getString(com.zitherharp.music.R.string.no_information)
                    }
                    audioName.text = videoTitle.text
                    albumName.text = videoTitle.text
                    artistName.text = artistTitle.text
                    closeDialog.setOnClickListener {
                        dismiss()
                    }
                }
            }
        }
    }

    class CommentDialog : BottomSheetDialogFragment() {
        private val binding: VideoCommentDialogBinding by lazy { VideoCommentDialogBinding.inflate(layoutInflater) }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            with(binding) {
                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                with(commentBox) {
                    if (requireArguments().getBoolean("showKeyboard")) {
                        requestFocus()
                        inputMethodManager.showSoftInput(commentBox, InputMethodManager.SHOW_IMPLICIT)
                        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                    }
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
}