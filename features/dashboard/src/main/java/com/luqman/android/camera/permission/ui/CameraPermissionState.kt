package com.luqman.android.camera.permission.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.luqman.android.camera.permission.utils.shouldShowPermissionRationale

sealed interface CameraPermissionState {
    object Granted : CameraPermissionState
    object Denied : CameraPermissionState
    object ShowRationale : CameraPermissionState
    object DeniedPermanently : CameraPermissionState
}

fun getCameraPermissionStatus(
    context: Context,
    activity: Activity
) = when {
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED -> CameraPermissionState.Granted

    activity.shouldShowPermissionRationale(
        permission = Manifest.permission.CAMERA
    ) -> CameraPermissionState.ShowRationale

    else -> CameraPermissionState.Denied
}