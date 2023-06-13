package at.aau.edu.appdev.messenger.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.appdev.messenger.ui.util.SingleLiveEvent
import at.aau.edu.appdev.messenger.persistence.UserRepository

class MainViewModel(
    userRepository: UserRepository,
) : ViewModel() {

    private val _openSetting = SingleLiveEvent<Boolean>()
    val openSettings: LiveData<Boolean> = _openSetting

    init {
        if (userRepository.getUser() == null) {
            _openSetting.postValue(true)
        } else {
            _openSetting.postValue(false)
        }
    }
}