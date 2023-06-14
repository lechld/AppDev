package at.aau.edu.appdev.messenger.model

import kotlinx.serialization.Serializable

@Serializable
sealed class MessageEvent {

    @Serializable
    data class UserJoined(val user: User) : MessageEvent()

    @Serializable
    data class UserLeft(val user: User) : MessageEvent()

    @Serializable
    object RoomClosed : MessageEvent()

    @Serializable
    data class Content(val message: Message) : MessageEvent()
}

