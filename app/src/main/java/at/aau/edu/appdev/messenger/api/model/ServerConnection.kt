package at.aau.edu.appdev.messenger.api.model

sealed interface ServerConnection : Connection {

    data class Connected(
        override val endpointId: String,
        override val endpointName: String
    ) : ServerConnection

    data class Failure(
        override val endpointId: String,
        override val endpointName: String
    ) : ServerConnection
}