package at.aau.edu.appdev.messenger.api

import android.content.Context
import at.aau.edu.appdev.messenger.api.impl.ServerImpl
import at.aau.edu.appdev.messenger.api.model.ServerConnection
import com.google.android.gms.nearby.Nearby
import kotlinx.coroutines.flow.Flow

interface Server : MessageSender, MessageReceiver {
    val connections: Flow<List<ServerConnection>>
    fun getConnectionsSync(): List<ServerConnection>

    fun startBroadcasting()
    fun stopBroadcasting()

    companion object {
        private var instance: Server? = null

        fun getInstance(context: Context): Server {
            val instance = instance

            if (instance != null) {
                return instance
            }

            val newInstance = ServerImpl(
                connectionsClient = Nearby.getConnectionsClient(context)
            )

            Companion.instance = newInstance
            return newInstance
        }
    }
}

