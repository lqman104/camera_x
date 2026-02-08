package com.luqman.android.camera.permission.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.luqman.android.camera.permission.utils.shouldShowPermissionRationale

@Composable
fun CameraScaffold(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit,
    state: (CameraPermissionState) -> CameraPermissionState
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val activity = LocalActivity.current
    val safeActivity = activity ?: context as Activity
    var permissionState by remember {
        mutableStateOf(getCameraPermissionStatus(context, safeActivity))
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionState = when {
                isGranted -> CameraPermissionState.Granted

                safeActivity.shouldShowPermissionRationale(
                    permission = Manifest.permission.CAMERA
                ) -> CameraPermissionState.ShowRationale

                else -> CameraPermissionState.DeniedPermanently
            }
        }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            val currentState = getCameraPermissionStatus(context, safeActivity)
            if (event == Lifecycle.Event.ON_RESUME && currentState == CameraPermissionState.Granted) {
                permissionState = state(currentState)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(modifier) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (permissionState) {
                CameraPermissionState.Granted -> children()
                CameraPermissionState.Denied -> {
                    LaunchedEffect(Unit) {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }

                CameraPermissionState.DeniedPermanently -> PermissionDeniedContent(
                    message = "Akses ke kamera tidak diizinkan,\n" +
                            "mohon untuk mengizinkan secara manual di halaman setting",
                    buttonText = "Buka Pengaturan"
                ) {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    context.startActivity(intent)
                }

                CameraPermissionState.ShowRationale -> PermissionDeniedContent(
                    message = "Akses ke kamera diperlukan untuk mengambil gambar.",
                    buttonText = "Buka Pengaturan"
                ) {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }
    }
}