package at.aau.edu.appdev.messenger.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.NavHostFragment
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.api.impl.REQUIRED_PERMISSIONS
import at.aau.edu.appdev.messenger.databinding.ActivityMainBinding
import at.aau.edu.appdev.messenger.permission.PermissionHandler
import at.aau.edu.appdev.messenger.persistence.UserRepository
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        viewModelFactory {
            initializer {
                MainViewModel(UserRepository(this@MainActivity))
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPermissionHandler()
        setupNavigation()
    }

    private fun setupPermissionHandler() {
        val permissionHandler = PermissionHandler(
            activity = this,
            permissions = REQUIRED_PERMISSIONS
        ) { permissions ->
            val anyDenied = permissions.any { !it.value }
            if (!anyDenied) {
                return@PermissionHandler
            }

            Snackbar.make(
                binding.root,
                getString(R.string.permission_hint),
                Snackbar.LENGTH_SHORT
            ).show()
        }

        lifecycle.addObserver(permissionHandler)
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        viewModel.openSettings.observe(this) { openSettings ->
            if (openSettings) {
                navController.navigate(R.id.user)
            }
        }
    }
}