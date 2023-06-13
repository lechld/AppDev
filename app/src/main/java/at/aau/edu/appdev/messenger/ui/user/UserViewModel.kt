package at.aau.edu.appdev.messenger.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.appdev.messenger.user.User
import at.aau.edu.appdev.messenger.user.UserColor
import at.aau.edu.appdev.messenger.user.UserRepository

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableLiveData<User>(userRepository.getUser())
    val user: LiveData<User> = _user

    private val _colors = MutableLiveData(UserColor.values().toList())
    val colors: LiveData<List<UserColor>> = _colors

    fun update(name: String, color: UserColor) {
        userRepository.saveUser(name, color)
    }
}