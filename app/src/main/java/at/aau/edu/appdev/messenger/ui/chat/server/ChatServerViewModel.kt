package at.aau.edu.appdev.messenger.ui.chat.server

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.model.Message

class ChatServerViewModel(
    private val server: Server,
) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages
}