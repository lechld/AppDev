package at.aau.edu.appdev.messenger.api.model

sealed interface ClientConnection : Connection {
    data class Found(
        override val endpointId: String,
        override val endpointName: String,
    ) : ClientConnection

    data class Connected(
        override val endpointId: String,
        override val endpointName: String,
    ) : ClientConnection

    data class Failure(
        override val endpointId: String,
        override val endpointName: String,
    ) : ClientConnection
}