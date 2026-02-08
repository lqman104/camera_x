package com.luqman.android.camera.permission.utils

import android.app.Activity
import androidx.core.app.ActivityCompat

fun Activity.shouldShowPermissionRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)