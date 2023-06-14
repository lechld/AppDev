package at.aau.edu.appdev.messenger.api.model

import at.aau.edu.appdev.messenger.model.User

interface Connection {
    val endpointId: String
    val user: User
}