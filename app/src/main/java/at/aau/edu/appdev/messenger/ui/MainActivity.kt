package at.aau.edu.appdev.messenger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.aau.edu.appdev.messenger.R
import at.aau.edu.appdev.messenger.api.impl.REQUIRED_PERMISSIONS
import at.aau.edu.appdev.messenger.databinding.ActivityMainBinding
import at.aau.edu.appdev.messenger.permission.PermissionHandler
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPermissionHandler()
    }

    private fun setupPermissionHandler() {
        val permissionHandler = PermissionHandler(
            activity = this,
            permissions = REQUIRED_PERMISSIONS
        ) { permissions ->
            val anyDenied = permissions.any { !it.value }
            if (anyDenied) {
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
}