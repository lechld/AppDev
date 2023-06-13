package at.aau.edu.appdev.messenger.user

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.util.UUID

private const val USER_PREFS_NAME = "messenger_user_prefs"
private const val USER_ID_KEY = "messenger_user_id"
private const val USER_NAME_KEY = "messenger_user_name"

class UserRepository(context: Context) {

    private val prefs = context.getSharedPreferences(USER_PREFS_NAME, MODE_PRIVATE)

    fun getUser(): User? {
        val id = prefs.getString(USER_ID_KEY, null) ?: return null
        val name = prefs.getString(USER_NAME_KEY, null) ?: return null

        return User(id, name)
    }

    fun saveUser(name: String) {
        val id = prefs.getString(USER_ID_KEY, UUID.randomUUID().toString())

        with(prefs.edit()) {
            putString(USER_ID_KEY, id)
            putString(USER_NAME_KEY, name)
            apply()
        }
    }
}