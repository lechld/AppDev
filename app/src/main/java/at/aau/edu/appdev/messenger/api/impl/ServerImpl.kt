package at.aau.edu.appdev.messenger.api.impl

import at.aau.edu.appdev.messenger.BuildConfig
import at.aau.edu.appdev.messenger.api.MessageReceiver
import at.aau.edu.appdev.messenger.api.MessageSender
import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.api.model.ServerConnection
import at.aau.edu.appdev.messenger.model.User
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ServerImpl(
    private val connectionsClient: ConnectionsClient,
    private val user: User?,
    private val applicationIdentifier: String = BuildConfig.APPLICATION_ID,
    private val messageDelegate: MessageDelegate = MessageDelegate(connectionsClient),
) : Server,
    MessageSender by messageDelegate,
    MessageReceiver by messageDelegate {

    private val _connections = MutableStateFlow<List<ServerConnection>>(emptyList())
    override val connections: StateFlow<List<ServerConnection>> = _connections.asStateFlow()

    override fun getConnectionsSync(): List<ServerConnection> {
        return _connections.value
    }

    override fun startBroadcasting() {
        connectionsClient.stopAllEndpoints()

        val connections = mutableListOf<ServerConnection>()

        connectionsClient.startAdvertising(
            Json.encodeToString(user),
            applicationIdentifier,
            object : ConnectionLifecycleCallback() {
                override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
                    val jsonUser = info.endpointName
                    val user = Json.decodeFromString<User>(jsonUser).copy(id = endpointId)

                    connections.add(ServerConnection.Requested(endpointId, user))

                    // Always allow incoming connection
                    connectionsClient.acceptConnection(endpointId, messageDelegate)
                }

                override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                    val old = connections.first { it.endpointId == endpointId }
                    val new = if (result.status.isSuccess) {
                        ServerConnection.Connected(endpointId, old.user)
                    } else {
                        ServerConnection.Failure(endpointId, old.user)
                    }

                    connections.remove(old)
                    connections.add(new)

                    _connections.tryEmit(connections)
                }

                override fun onDisconnected(endpointId: String) {
                    val old = connections.first { it.endpointId == endpointId }
                    val new = ServerConnection.Failure(endpointId, old.user)

                    connections.remove(old)
                    connections.add(new)

                    _connections.tryEmit(connections)
                }
            },
            advertisingOptions
        )
    }

    override fun stopBroadcasting() {
        connectionsClient.stopAdvertising()
    }
}