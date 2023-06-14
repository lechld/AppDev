package at.aau.edu.appdev.messenger.ui.chat

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.appdev.messenger.api.MessageSender
import at.aau.edu.appdev.messenger.model.Message
import at.aau.edu.appdev.messenger.model.MessageEvent
import at.aau.edu.appdev.messenger.model.User
import at.aau.edu.appdev.messenger.model.UserColor
import at.aau.edu.appdev.messenger.persistence.UserRepository
import java.time.OffsetDateTime
import java.util.UUID

abstract class ChatFragmentViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _events = MutableLiveData<List<MessageEvent>>()
    val events: LiveData<List<MessageEvent>> = _events

    fun sendMessage(bitmap: Bitmap?, text: String?) {
        val user = userRepository.enforceUser()
        if (text.isNullOrEmpty() && bitmap == null) {
            return
        }
        val id = UUID.randomUUID().toString()
        val now = OffsetDateTime.now()
        val content = text ?: ""

        val message = if (bitmap != null) {
            Message.Drawing(
                sender = user,
                time = now,
                id = id,
                bitmap = resizeBitmap(bitmap),
                text = content
            )
        } else {
            Message.Text(
                sender = user,
                time = now,
                id = id,
                content = content
            )
        }

        val event = MessageEvent.Content(message)

        addEvent(event)
        sendEvent(event)
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
        val width = 150f
        val height = (width / ratio)

        return Bitmap.createScaledBitmap(
            bitmap, width.toInt(), height.toInt(), false
        )
    }

    protected fun addEvent(event: MessageEvent) {
        val current = _events.value ?: emptyList()
        val new = mutableListOf<MessageEvent>()

        new.addAll(current)
        new.add(event)

        _events.postValue(new)
    }

    abstract fun sendEvent(event: MessageEvent)
}