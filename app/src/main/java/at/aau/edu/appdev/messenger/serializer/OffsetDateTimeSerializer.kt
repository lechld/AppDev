package at.aau.edu.appdev.messenger.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private val FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME

class OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "OffsetDateTime",
        kind = PrimitiveKind.STRING
    )

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val stringValue = decoder.decodeString()

        return OffsetDateTime.parse(stringValue)
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        val stringValue = FORMATTER.format(value)

        encoder.encodeString(stringValue)
    }
}