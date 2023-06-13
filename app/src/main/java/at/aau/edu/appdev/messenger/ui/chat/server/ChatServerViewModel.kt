package at.aau.edu.appdev.messenger.ui.chat.server

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.model.Message

class ChatServerViewModel(
    private val server: Server,
) : ViewModel() {
    fun sendMessage(bitmap: Bitmap?, text: String?) {
        println(bitmap)
        println(text)

    }

    private fun resizeBitmap(bitmap: Bitmap) {
        val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
        val width = 150f
        val height = (width / ratio)

        Bitmap.createScaledBitmap(
            bitmap, width.toInt(), height.toInt(), false
        )
    }

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages
}