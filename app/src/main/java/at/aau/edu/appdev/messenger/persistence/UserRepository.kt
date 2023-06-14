package at.aau.edu.appdev.messenger.persistence

import android.content.Context
import at.aau.edu.appdev.messenger.model.User
import at.aau.edu.appdev.messenger.model.UserColor
import java.util.UUID

private const val USER_PREFS_NAME = "messenger_user_prefs"
private const val USER_ID_KEY = "messenger_user_id"
private const val USER_NAME_KEY = "messenger_user_name"
private const val USER_COLOR_KEY = "messenger_user_color"

class UserRepository(context: Context) {

    private val prefs = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)

    fun getUser(): User? {
        val id = prefs.getString(USER_ID_KEY, null) ?: return null
        val name = prefs.getString(USER_NAME_KEY, null) ?: return null
        val colorName = prefs.getString(USER_COLOR_KEY, UserColor.VIOLET.name) ?: return null
        val color = UserColor.valueOf(colorName)

        return User(id, name, color)
    }

    fun saveUser(name: String, color: UserColor) {
        val id = prefs.getString(USER_ID_KEY, UUID.randomUUID().toString())

        with(prefs.edit()) {
            putString(USER_ID_KEY, id)
            putString(USER_NAME_KEY, name)
            putString(USER_COLOR_KEY, color.name)
            apply()
        }
    }
}