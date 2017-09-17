package com.arcao.opencaching4locus.ui.base.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

object PermissionUtil {
    private val REQUEST_PERMISSION_BASE = 100
    val REQUEST_LOCATION_PERMISSION = REQUEST_PERMISSION_BASE + 1
    val REQUEST_EXTERNAL_STORAGE_PERMISSION = REQUEST_PERMISSION_BASE + 2

    val PERMISSION_LOCATION_GPS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    val PERMISSION_LOCATION_WIFI = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    val PERMISSION_EXTERNAL_STORAGE = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
}

fun Context.verifyPermissions(grantResults: IntArray): Boolean {
    return grantResults.none {
        it != PackageManager.PERMISSION_GRANTED
    }
}

fun Context.hasPermission(vararg permissions: String): Boolean {
    return permissions.none {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED
    }
}
