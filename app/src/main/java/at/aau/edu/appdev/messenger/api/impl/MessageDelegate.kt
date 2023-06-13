package at.aau.edu.appdev.messenger.api.impl

import at.aau.edu.appdev.messenger.api.EMPTY_DATA
import at.aau.edu.appdev.messenger.api.MessageReceiver
import at.aau.edu.appdev.messenger.api.MessageSender
import at.aau.edu.appdev.messenger.api.model.Connection
import at.aau.edu.appdev.messenger.api.model.Data
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.nio.charset.Charset

class MessageDelegate(
    private val connectionsClient: ConnectionsClient,
    private val encoding: Charset = Charsets.UTF_8,
) : MessageReceiver, MessageSender, PayloadCallback() {

    // region MessageReceiver

    private val _messages = MutableStateFlow(EMPTY_DATA)
    override val messages: Flow<Data> = _messages

    // endregion

    // region MessageSender

    override fun send(connection: Connection, data: String) {
        val payload = Payload.fromBytes(data.toByteArray(encoding))

        connectionsClient.sendPayload(connection.endpointId, payload)
    }

    // endregion

    // region PayloadCallback

    override fun onPayloadReceived(endpointId: String, payload: Payload) {
        val value = payload.asBytes()?.let { bytes ->
            String(bytes, encoding)
        } ?: ""
        val data = Data(endpointId, value)

        _messages.tryEmit(data)
    }

    override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
        // Stub
    }

    // endregion
}