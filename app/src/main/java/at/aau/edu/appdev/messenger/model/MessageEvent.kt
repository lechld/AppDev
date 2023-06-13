package at.aau.edu.appdev.messenger.model

import at.aau.edu.appdev.messenger.user.User

sealed class MessageEvent {
    data class UserJoined(val user: User) : MessageEvent()
    data class UserLeft(val user: User) : MessageEvent()
    object RoomClosed : MessageEvent()
    data class Content(val message: Message) : MessageEvent()
}