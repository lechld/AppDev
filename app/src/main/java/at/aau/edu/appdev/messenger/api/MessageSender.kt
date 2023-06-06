package at.aau.edu.appdev.messenger.api

import at.aau.edu.appdev.messenger.api.model.Connection

interface MessageSender {
    fun send(connection: Connection, data: String)
}