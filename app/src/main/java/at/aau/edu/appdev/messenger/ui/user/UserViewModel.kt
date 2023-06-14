package at.aau.edu.appdev.messenger.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.appdev.messenger.model.User
import at.aau.edu.appdev.messenger.model.UserColor
import at.aau.edu.appdev.messenger.persistence.UserRepository

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableLiveData<User>(userRepository.getUser())
    val user: LiveData<User> = _user

    fun update(name: String, color: UserColor) {
        userRepository.saveUser(name, color)
    }
}