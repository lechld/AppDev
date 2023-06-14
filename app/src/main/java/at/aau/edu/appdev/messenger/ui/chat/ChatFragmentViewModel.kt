package at.aau.edu.appdev.messenger.ui.chat

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.aau.edu.appdev.messenger.model.Message
import at.aau.edu.appdev.messenger.model.User
import at.aau.edu.appdev.messenger.model.UserColor
import at.aau.edu.appdev.messenger.persistence.UserRepository
import java.time.OffsetDateTime
import java.util.UUID

abstract class ChatFragmentViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    init {
        // TODO: Remove
        _messages.postValue(getDummyData(userRepository.enforceUser()))
    }

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

        val currentMessages = messages.value ?: emptyList()
        val newMessages = mutableListOf<Message>()

        newMessages.addAll(currentMessages)
        newMessages.add(message)

        // TODO: Send via server to other users

        _messages.postValue(newMessages)
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
        val width = 150f
        val height = (width / ratio)

        return Bitmap.createScaledBitmap(
            bitmap, width.toInt(), height.toInt(), false
        )
    }

    private fun getDummyData(user1: User): List<Message> {
        val user2 = User("Emily", "user2", UserColor.VIOLET)

        return listOf(
            Message.Text(
                user1,
                OffsetDateTime.parse("2023-06-13T10:00:00Z"),
                "1",
                "Hello"
            ),
            Message.Text(
                user2,
                OffsetDateTime.parse("2023-06-13T10:02:00Z"),
                "2",
                "Hi John, how are you?"
            ),
            Message.Text(
                user1,
                OffsetDateTime.parse("2023-06-13T10:05:00Z"),
                "3",
                "I'm good, thanks! How about you?"
            ),
            Message.Text(
                user2,
                OffsetDateTime.parse("2023-06-13T10:07:00Z"),
                "4",
                "I'm doing well too."
            ),
            Message.Text(
                user1,
                OffsetDateTime.parse("2023-06-13T10:10:00Z"),
                "5",
                "That's great to hear!"
            )
        )
    }

}