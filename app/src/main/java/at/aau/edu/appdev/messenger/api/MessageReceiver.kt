package at.aau.edu.appdev.messenger.api

import at.aau.edu.appdev.messenger.api.model.Data
import kotlinx.coroutines.flow.Flow

val EMPTY_DATA = Data("", "")

interface MessageReceiver {
    val messages: Flow<Data>
}