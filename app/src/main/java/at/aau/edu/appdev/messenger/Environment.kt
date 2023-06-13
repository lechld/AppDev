package at.aau.edu.appdev.messenger

import android.content.Context
import at.aau.edu.appdev.messenger.api.Client
import at.aau.edu.appdev.messenger.api.Server
import at.aau.edu.appdev.messenger.user.UserRepository

/**
 * Our Dependency Injection tool. Currently only holds singletons across the application
 * Project is too small to use something like Koin or Dagger.
 */
class Environment private constructor(
    context: Context
) {
    val userRepository by lazy {
        UserRepository(context)
    }

    val client by lazy {
        Client.getInstance(context, userRepository)
    }

    val server by lazy {
        Server.getInstance(context, userRepository)
    }

    companion object {
        private var instance: Environment? = null

        fun getInstance(context: Context): Environment {
            val instance = this.instance

            if (instance != null) {
                return instance
            }

            // Only rely on application here, we don't want to keep references on other context! Potential memory leaks!
            val newInstance = Environment(context.applicationContext)

            this.instance = newInstance

            return newInstance
        }
    }
}