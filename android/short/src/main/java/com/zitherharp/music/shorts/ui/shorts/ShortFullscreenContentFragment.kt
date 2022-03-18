package com.zitherharp.music.shorts.ui.shorts

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortFullscreenContentBinding

class ShortFullscreenContentFragment(): Fragment() {
    private lateinit var binding: ShortFullscreenContentBinding
    private var shorts: List<Short> = ArrayList()

    constructor(shorts: List<Short>): this() {
        this.shorts = shorts
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ShortFullscreenContentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel: ShortViewModel by viewModels()
        viewModel.position.observe(viewLifecycleOwner) {
            with(binding) {
                val short = shorts[it]
                audioName.text = short.getAudios().getName(Language.VIETNAMESE)
                artistName.text = short.getArtists().getName(Language.VIETNAMESE)
                var y1 = 0F
                var y2: Float
                shortView.setOnTouchListener { view, motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> y1 = view.y
                        MotionEvent.ACTION_UP -> {
                            y2 = view.y
                            val distance = y2 - y1
                            if (distance > 0) {
                                Toast.makeText(context, "Up", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Down", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    view.onTouchEvent(motionEvent)
                }

            }
        }
    }

    class ShortViewModel: ViewModel() {
        val position: LiveData<Int> = MutableLiveData<Int>().apply {
            value = 0
        }
    }

   inner class GestureListener: GestureDetector.OnGestureListener {
        var gestureScanner: GestureDetector? = null

        override fun onDown(p0: MotionEvent?): Boolean {
            TODO("Not yet implemented")
        }

        override fun onShowPress(p0: MotionEvent?) {
            TODO("Not yet implemented")
        }

        override fun onSingleTapUp(p0: MotionEvent?): Boolean {
            TODO("Not yet implemented")
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            TODO("Not yet implemented")
        }

        override fun onLongPress(p0: MotionEvent?) {
            TODO("Not yet implemented")
        }

        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
            TODO("Not yet implemented")
        }

    }
}