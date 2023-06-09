package at.aau.edu.appdev.messenger.api.impl

import at.aau.edu.appdev.messenger.BuildConfig
import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.api.MessageReceiver
import at.aau.edu.appdev.messenger.api.MessageSender
import at.aau.edu.appdev.messenger.api.model.ClientConnection
import at.aau.edu.appdev.messenger.model.User
import com.google.android.gms.nearby.connection.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class ClientImpl(
    private val connectionsClient: ConnectionsClient,
    private val userIdentifier: User?,
    private val applicationIdentifier: String = BuildConfig.APPLICATION_ID,
    private val messageDelegate: MessageDelegate = MessageDelegate(connectionsClient),
) : Client,
    MessageSender by messageDelegate,
    MessageReceiver by messageDelegate {

    private val _connections = MutableStateFlow(emptyList<ClientConnection>())
    override val connections: Flow<List<ClientConnection>> = _connections

    override fun connectionsSync(): List<ClientConnection> {
        return _connections.value
    }

    override fun startDiscovery() {
        connectionsClient.stopAllEndpoints()

        val connections = mutableListOf<ClientConnection>()

        connectionsClient.startDiscovery(
            applicationIdentifier,
            object : EndpointDiscoveryCallback() {
                override fun onEndpointFound(
                    endpointId: String,
                    endpointInfo: DiscoveredEndpointInfo
                ) {
                    val jsonUser = endpointInfo.endpointName
                    val user = Json.decodeFromString<User>(jsonUser).copy(id = endpointId)

                    connections.add(ClientConnection.Found(endpointId, user))

                    _connections.tryEmit(connections)
                }

                override fun onEndpointLost(endpointId: String) {
                    connections.removeAll { it.endpointId == endpointId }

                    _connections.tryEmit(connections)
                }

            },
            discoveryOptions
        )
    }

    override fun stopDiscovery() {
        connectionsClient.stopDiscovery()
    }

    override fun connect(connection: ClientConnection) {
        connectionsClient.requestConnection(
            Json.encodeToString(userIdentifier),
            connection.endpointId,
            object : ConnectionLifecycleCallback() {
                override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
                    // we selected to connect, therefore we can directly accept that connection
                    connectionsClient.acceptConnection(endpointId, messageDelegate)
                }

                override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                    val connections = connectionsSync().toMutableList()
                    val old = connections.first { it.endpointId == endpointId }
                    val new = if (result.status.isSuccess) {
                        ClientConnection.Connected(endpointId, old.user)
                    } else {
                        ClientConnection.Failure(endpointId, old.user)
                    }

                    connections.remove(old)
                    connections.add(new)

                    _connections.tryEmit(connections)
                }

                override fun onDisconnected(endpointId: String) {
                    val connections = _connections.value.toMutableList()
                    val old = connections.first { it.endpointId == endpointId }
                    val new = ClientConnection.Failure(endpointId, old.user)

                    connections.remove(old)
                    connections.add(new)

                    _connections.tryEmit(connections)
                }
            })
    }
}
