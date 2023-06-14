package at.aau.edu.appdev.messenger.api.model

import at.aau.edu.appdev.messenger.model.User

sealed interface ClientConnection : Connection {
    data class Found(
        override val endpointId: String,
        override val user: User,
    ) : ClientConnection

    data class Connected(
        override val endpointId: String,
        override val user: User,
    ) : ClientConnection

    data class Failure(
        override val endpointId: String,
        override val user: User,
    ) : ClientConnection
}