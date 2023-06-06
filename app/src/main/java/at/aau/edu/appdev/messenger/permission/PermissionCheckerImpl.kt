package at.aau.edu.appdev.messenger.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import at.aau.edu.appdev.messenger.permission.PermissionChecker

class PermissionCheckerImpl : PermissionChecker {

    override fun isGranted(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}