package com.audioquiz.permission.manager

import android.content.Context
import androidx.activity.ComponentActivity
//import androidx.activity.ComponentActivity
import com.audioquiz.permission.enum.PermissionType

interface PermissionManager {
    fun hasPermission(
        permission: PermissionType,
        context: Context,
    ): Boolean

    fun requestPermission(
        permission: PermissionType,
        activity: ComponentActivity,
        callback: (isGranted: Boolean) -> Unit,
    ): Unit?

    fun performPermission(
        permission: PermissionType,
        activity: ComponentActivity,
        callback: (isGranted: Boolean) -> Unit = {},
    ): Unit?
}
