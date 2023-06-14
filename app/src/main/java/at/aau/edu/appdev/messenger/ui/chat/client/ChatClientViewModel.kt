package at.aau.edu.appdev.messenger.ui.chat.client

import androidx.lifecycle.viewModelScope
import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.api.model.ClientConnection
import at.aau.edu.appdev.messenger.model.MessageEvent
import at.aau.edu.appdev.messenger.persistence.UserRepository
import at.aau.edu.appdev.messenger.ui.chat.ChatFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatClientViewModel(
    private val client: Client,
    userRepository: UserRepository,
) : ChatFragmentViewModel(userRepository) {

    init {
        collectEvents()
    }

    override fun collectEvents() {
        viewModelScope.launch {
            client.messages.collect {
                addEvent(it)
            }
        }
    }

    override fun sendEvent(event: MessageEvent) {
        client.connectionsSync().filterIsInstance(ClientConnection.Connected::class.java)
            .forEach { connection ->
                viewModelScope.launch(Dispatchers.IO) {
                    client.send(connection, event)
                }
            }
    }
}