package at.aau.edu.appdev.messenger.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.appdev.messenger.user.User
import at.aau.edu.appdev.messenger.user.UserRepository

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableLiveData<User>(userRepository.getUser())
    val user: LiveData<User> = _user

    fun update(name: String) {
        userRepository.saveUser(name)
    }
}