package at.aau.edu.appdev.messenger.api

import android.content.Context
import at.aau.edu.appdev.messenger.api.impl.ClientImpl
import at.aau.edu.appdev.messenger.api.model.ClientConnection
import com.google.android.gms.nearby.Nearby
import kotlinx.coroutines.flow.Flow

interface Client : MessageSender, MessageReceiver {
    val connections: Flow<List<ClientConnection>>
    fun connectionsSync(): List<ClientConnection>

    fun startDiscovery()
    fun stopDiscovery()

    fun connect(connection: ClientConnection)

    companion object {
        private var instance: Client? = null

        fun getInstance(context: Context): Client {
            val instance = instance

            if (instance != null) {
                return instance
            }

            val newInstance = ClientImpl(
                connectionsClient = Nearby.getConnectionsClient(context)
            )

            Companion.instance = newInstance
            return newInstance
        }
    }
}