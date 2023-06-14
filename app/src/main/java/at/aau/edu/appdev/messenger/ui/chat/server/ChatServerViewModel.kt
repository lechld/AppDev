package at.aau.edu.appdev.messenger.ui.chat.server

import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.persistence.UserRepository
import at.aau.edu.appdev.messenger.ui.chat.ChatFragmentViewModel

class ChatServerViewModel(
    private val server: Server,
    userRepository: UserRepository,
) : ChatFragmentViewModel(userRepository) {

    fun startBroadcasting() {
        server.startBroadcasting()
    }

    fun stopBroadcasting() {
        server.stopBroadcasting()
    }
}