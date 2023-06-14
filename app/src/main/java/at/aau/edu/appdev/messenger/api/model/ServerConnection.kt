package at.aau.edu.appdev.messenger.api.model

import at.aau.edu.appdev.messenger.model.User

sealed interface ServerConnection : Connection {

    data class Requested(
        override val endpointId: String,
        override val user: User,
    ): ServerConnection

    data class Connected(
        override val endpointId: String,
        override val user: User,
    ) : ServerConnection

    data class Failure(
        override val endpointId: String,
        override val user: User,
    ) : ServerConnection
}