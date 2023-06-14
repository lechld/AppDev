package at.aau.edu.appdev.messenger.api

import at.aau.edu.appdev.messenger.model.MessageEvent
import kotlinx.coroutines.flow.Flow

interface MessageReceiver {
    val messages: Flow<MessageEvent>
}