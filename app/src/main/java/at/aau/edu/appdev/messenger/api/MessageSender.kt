package at.aau.edu.appdev.messenger.api

import at.aau.edu.appdev.messenger.api.model.Connection
import at.aau.edu.appdev.messenger.model.MessageEvent

interface MessageSender {
    fun send(connection: Connection, messageEvent: MessageEvent)
}