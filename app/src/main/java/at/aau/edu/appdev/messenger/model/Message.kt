package at.aau.edu.appdev.messenger.model

import at.aau.edu.appdev.messenger.user.User
import java.time.OffsetDateTime

sealed interface Message {

    val sender: User
    val time: OffsetDateTime
    val id: String

    data class Text(
        override val sender: User,
        override val time: OffsetDateTime,
        override val id: String,
        val content: String,
    ) : Message

    data class Drawing(
        override val sender: User,
        override val time: OffsetDateTime,
        override val id: String,
        // TODO: How to add bitmap data?
        val text: String,
    ): Message
}