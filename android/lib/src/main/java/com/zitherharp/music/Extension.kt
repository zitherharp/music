package com.zitherharp.music

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.zitherharp.music.Extension.showKeyboard
import com.zitherharp.music.core.Spreadsheet
import com.zitherharp.music.core.Youtube
import kotlinx.coroutines.*
import java.net.URL

object Extension {
    fun Context.isNetworkConnected() =
        Runtime.getRuntime().exec("ping -c 1 google.com").waitFor() == 0

    @OptIn(DelicateCoroutinesApi::class)
    fun ImageView.setImageUrl(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val image = BitmapFactory.decodeStream(
                    URL(url).openConnection().apply {
                        useCaches = true
                    }.getInputStream())
                withContext(Dispatchers.Main) {
                    setImageBitmap(image)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun Context.share(text: String) {
        startActivity(Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }, getString(R.string.share)))
    }

    fun EditText.showKeyboard() {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
            requestFocus()

        }
    }
}