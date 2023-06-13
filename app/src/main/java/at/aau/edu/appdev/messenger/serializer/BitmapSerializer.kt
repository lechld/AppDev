package at.aau.edu.appdev.messenger.serializer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.io.ByteArrayOutputStream
import java.util.Base64

class BitmapSerializer : KSerializer<Bitmap> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Bitmap",
        kind = PrimitiveKind.STRING,
    )

    override fun deserialize(decoder: Decoder): Bitmap {
        val stringValue = decoder.decodeString()
        val base64 = Base64.getDecoder().decode(stringValue)

        return BitmapFactory.decodeByteArray(base64, 0, base64.size)
    }

    override fun serialize(encoder: Encoder, value: Bitmap) {
        val stream = ByteArrayOutputStream()

        value.compress(Bitmap.CompressFormat.PNG, 100, stream) // TODO: Double check if needed

        val bytes = stream.toByteArray()
        val stringValue = Base64.getEncoder().encodeToString(bytes)

        encoder.encodeString(stringValue)
    }
}