package com.zitherharp.music.tv

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.OnItemViewSelectedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Music
import com.zitherharp.music.model.Video
import com.zitherharp.music.tv.presenter.VideoPresenter
import kotlinx.coroutines.*
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

/**
 * Loads a grid of cards with movies to browse.
 */
@DelicateCoroutinesApi
class MainFragment: BrowseSupportFragment() {

    private val mHandler = Handler()
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBackgroundManager()
        setupUIElements()
        loadRows()
        setupEventListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(requireActivity().window)
        mDefaultBackground = ContextCompat.getDrawable(requireActivity(), R.drawable.default_background)
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupUIElements() {
        title = getString(com.zitherharp.music.R.string.video_app_name)
        // over title
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(requireActivity(), R.color.fastlane_background)
        // set search icon color
        searchAffordanceColor = ContextCompat.getColor(requireActivity(), R.color.search_opaque)
    }

    private fun loadRows() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = Video.repository.values.shuffled()
            withContext(Dispatchers.Main) {
                val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
                val videoPresenter = VideoPresenter()
                for (i in 0 until Music.values().size) {
                    val listRowAdapter = ArrayObjectAdapter(videoPresenter)
                    for (j in 0 until NUM_COLS) {
                        listRowAdapter.add(list[j % 5])
                    }
                    val header = HeaderItem(i.toLong(), Music.values()[i].name)
                    rowsAdapter.add(ListRow(header, listRowAdapter))
                }
                val gridHeader = HeaderItem(NUM_ROWS.toLong(), "PREFERENCES")
                val mGridPresenter = GridItemPresenter()
                val gridRowAdapter = ArrayObjectAdapter(mGridPresenter)
                gridRowAdapter.add(resources.getString(R.string.grid_view))
                gridRowAdapter.add(getString(R.string.error_fragment))
                gridRowAdapter.add(resources.getString(R.string.personal_settings))
                rowsAdapter.add(ListRow(gridHeader, gridRowAdapter))
                adapter = rowsAdapter
            }
        }
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            Toast.makeText(requireActivity(),
                "Implement your own in-app search", Toast.LENGTH_LONG)
                .show()
        }

        onItemViewClickedListener = ItemViewClickedListener()
        setOnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
            if (item is Video) {
                mBackgroundUri = item.getImageUrl(Youtube.Image.SDDEFAULT)
                startBackgroundTimer()
            }
        }
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder,
            item: Any, rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            if (item is Movie) {
                Log.d(TAG, "Item: $item")
                val intent = Intent(activity!!, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.MOVIE, item)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity!!,
                    (itemViewHolder.view as ImageCardView).mainImageView,
                    DetailsActivity.SHARED_ELEMENT_NAME).toBundle()
                startActivity(intent, bundle)
            } else if (item is String) {
                if (item.contains(getString(R.string.error_fragment))) {
                    val intent = Intent(activity!!, BrowseErrorActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity!!, item, Toast.LENGTH_SHORT).show()
                }
            } else if (item is Video) {
                startActivity(Intent(context, PlaybackActivity::class.java).apply {
                    putExtra(PlaybackActivity::class.java.name, item.id)
                })
            }
        }
    }

    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(requireActivity())
            .load(uri)
            .centerCrop()
            .error(mDefaultBackground)
            .into<SimpleTarget<Drawable>>(
                object : SimpleTarget<Drawable>(width, height) {
                    override fun onResourceReady(
                        drawable: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        mBackgroundManager.drawable = drawable
                    }
                })
        mBackgroundTimer?.cancel()
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(object: TimerTask() {
            override fun run() {
                mHandler.post { updateBackground(mBackgroundUri) }
            }
        }, BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class GridItemPresenter : Presenter() {
        override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
            val view = TextView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT)
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.default_background))
            view.setTextColor(Color.WHITE)
            view.gravity = Gravity.CENTER
            return ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
            (viewHolder.view as TextView).text = item as String
        }

        override fun onUnbindViewHolder(viewHolder: ViewHolder) {}
    }

    companion object {
        private val TAG = "MainFragment"

        private val BACKGROUND_UPDATE_DELAY = 300
        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
        private val NUM_ROWS = 6
        private val NUM_COLS = 15
    }
}