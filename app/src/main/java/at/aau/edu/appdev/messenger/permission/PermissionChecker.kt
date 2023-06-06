package at.aau.edu.appdev.messenger.permission

import android.content.Context

interface PermissionChecker {
    fun isGranted(context: Context, permission: String): Boolean
}