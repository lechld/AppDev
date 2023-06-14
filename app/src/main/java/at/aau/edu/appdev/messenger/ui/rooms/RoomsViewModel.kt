package at.aau.edu.appdev.messenger.ui.rooms

import androidx.lifecycle.*
import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.api.model.ClientConnection
import at.aau.edu.appdev.messenger.api.model.Connection
import at.aau.edu.appdev.messenger.model.JOKES
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RoomsViewModel(
    private val client: Client?,
) : ViewModel() {

    val rooms: LiveData<List<Connection>>? = client?.connections?.map {
        it.filterIsInstance<ClientConnection.Found>()
    }?.asLiveData()

    private val _jokes = MutableLiveData<String>()
    val jokes: LiveData<String> = _jokes.distinctUntilChanged()

    init {
        viewModelScope.launch {
            updateJoke()
        }
    }

    fun startDiscovery() {
        client?.startDiscovery()
    }

    fun stopDiscovery() {
        client?.stopDiscovery()
    }

    private suspend fun updateJoke() {
        _jokes.postValue(JOKES.random())
        delay(5000)
        updateJoke()
    }

    fun connect(connection: Connection) {
        client?.connect(connection as ClientConnection)
    }
}