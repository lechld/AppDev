package at.aau.edu.appdev.messenger.ui.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.api.model.ClientConnection
import at.aau.edu.appdev.messenger.api.model.Connection
import kotlinx.coroutines.flow.map

class RoomsViewModel(
    client: Client,
) : ViewModel() {

    val rooms: LiveData<List<Connection>> = client.connections.map {
        it.filterIsInstance<ClientConnection.Found>()
    }.asLiveData()
}