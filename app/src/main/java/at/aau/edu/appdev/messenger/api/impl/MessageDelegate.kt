package at.aau.edu.appdev.messenger.api.impl

import at.aau.edu.appdev.messenger.api.MessageReceiver
import at.aau.edu.appdev.messenger.api.MessageSender
import at.aau.edu.appdev.messenger.api.model.Connection
import at.aau.edu.appdev.messenger.model.MessageEvent
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

class MessageDelegate(
    private val connectionsClient: ConnectionsClient,
    private val encoding: Charset = Charsets.UTF_8,
) : MessageReceiver, MessageSender, PayloadCallback() {

    // region MessageReceiver

    private val _messages = MutableStateFlow<MessageEvent>(MessageEvent.Empty)
    override val messages: Flow<MessageEvent> = _messages

    // endregion

    // region MessageSender

    override fun send(connection: Connection, messageEvent: MessageEvent) {
        val json = Json.encodeToString(messageEvent)
        val payload = Payload.fromBytes(json.toByteArray(encoding))

        connectionsClient.sendPayload(connection.endpointId, payload)
    }

    // endregion

    // region PayloadCallback

    override fun onPayloadReceived(endpointId: String, payload: Payload) {
        val json = payload.asBytes()?.let { bytes ->
            String(bytes, encoding)
        } ?: return
        val messageEvent = Json.decodeFromString<MessageEvent>(json)

        _messages.tryEmit(messageEvent)
    }

    override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
        // Stub
    }

    // endregion
}