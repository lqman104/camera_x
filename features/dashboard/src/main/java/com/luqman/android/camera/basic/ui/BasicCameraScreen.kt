package com.luqman.android.camera.basic.ui

import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.luqman.android.camera.permission.ui.CameraScaffold
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun BasicCameraScreen() {
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    val surfaceRequests = remember {
        MutableStateFlow<SurfaceRequest?>(null)
    }
    val surfaceRequest by surfaceRequests.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        val provider = ProcessCameraProvider.awaitInstance(context = context)
        val preview = Preview.Builder().build().apply {
            setSurfaceProvider { request ->
                surfaceRequests.value = request
            }
        }

        provider.unbindAll()
        provider.bindToLifecycle(lifeCycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview)
    }

    CameraScaffold {
        surfaceRequest?.let { req ->
            CameraXViewfinder(surfaceRequest = req, modifier = Modifier.fillMaxSize())
        }
    }
}