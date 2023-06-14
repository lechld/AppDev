package at.aau.edu.appdev.messenger.ui.chat.server

import androidx.lifecycle.viewModelScope
import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.api.model.ServerConnection
import at.aau.edu.appdev.messenger.model.MessageEvent
import at.aau.edu.appdev.messenger.model.User
import at.aau.edu.appdev.messenger.persistence.UserRepository
import at.aau.edu.appdev.messenger.ui.chat.ChatFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatServerViewModel(
    private val server: Server,
    userRepository: UserRepository,
) : ChatFragmentViewModel(userRepository) {

    init {
        registerUserEvents()
    }

    fun startBroadcasting() {
        server.startBroadcasting()
    }

    fun stopBroadcasting() {
        server.stopBroadcasting()
    }

    override fun sendEvent(event: MessageEvent) {
        server.getConnectionsSync().filterIsInstance(ServerConnection.Connected::class.java)
            .forEach { connection ->
                viewModelScope.launch(Dispatchers.IO) {
                    server.send(connection, event)
                }
            }
    }

    private fun registerUserEvents() {
        viewModelScope.launch {
            val inRoom = mutableListOf<User>()

            server.connections.collect { newConnections ->
                val connectionFailure =
                    newConnections.filterIsInstance(ServerConnection.Failure::class.java)
                        .map { it.user }
                val lostUsers = inRoom.filter { connectionFailure.contains(it) }
                val lostUserEvents = lostUsers.map { MessageEvent.UserLeft(it) }

                val connectionSuccess =
                    newConnections.filterIsInstance(ServerConnection.Connected::class.java)
                val connectionSuccessUsers = connectionSuccess.map { it.user }
                val newUsers = connectionSuccessUsers.filter { !inRoom.contains(it) }
                val joinedUserEvents = newUsers.map { MessageEvent.UserJoined(it) }

                inRoom.removeAll(lostUsers)
                inRoom.addAll(newUsers)

                lostUserEvents.forEach { event ->
                    sendEvent(event)
                    addEvent(event)
                }
                joinedUserEvents.forEach { event ->
                    sendEvent(event)
                    addEvent(event)
                }
            }
        }
    }
}