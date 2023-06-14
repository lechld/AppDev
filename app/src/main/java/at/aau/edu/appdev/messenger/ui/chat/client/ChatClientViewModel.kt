package at.aau.edu.appdev.messenger.ui.chat.client

import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.persistence.UserRepository
import at.aau.edu.appdev.messenger.ui.chat.ChatFragmentViewModel

class ChatClientViewModel(
    private val client: Client,
    private val userRepository: UserRepository,
) : ChatFragmentViewModel(userRepository)