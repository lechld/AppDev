package at.aau.edu.appdev.messenger.model

import android.graphics.Bitmap
import at.aau.edu.appdev.messenger.serializer.BitmapSerializer
import at.aau.edu.appdev.messenger.serializer.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
sealed interface Message {

    val sender: User
    val time: OffsetDateTime
    val id: String

    @Serializable
    data class Text(
        override val sender: User,
        @Serializable(OffsetDateTimeSerializer::class)
        override val time: OffsetDateTime,
        override val id: String,
        val content: String,
    ) : Message

    @Serializable
    data class Drawing(
        override val sender: User,
        @Serializable(OffsetDateTimeSerializer::class)
        override val time: OffsetDateTime,
        override val id: String,
        @Serializable(BitmapSerializer::class)
        val bitmap: Bitmap,
        val text: String,
    ) : Message
}