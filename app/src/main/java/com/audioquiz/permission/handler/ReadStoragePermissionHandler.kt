package com.audioquiz.permission.handler

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.audioquiz.permission.enum.PermissionType

class ReadStoragePermissionHandler : PermissionHandler {
    override val permission: String = Manifest.permission.READ_EXTERNAL_STORAGE

    override val permissionType: PermissionType = PermissionType.READ_EXTERNAL_STORAGE

    override fun hasPermission(
        context: Context
    ): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
}
