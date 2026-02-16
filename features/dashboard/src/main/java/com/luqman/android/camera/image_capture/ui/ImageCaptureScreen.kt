package com.luqman.android.camera.image_capture.ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.luqman.android.camera.core.ui.theme.CameraTheme
import com.luqman.android.camera.permission.ui.CameraScaffold
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.Preview as CameraPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCaptureScreen(
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val surfaceRequests = remember {
        MutableStateFlow<SurfaceRequest?>(null)
    }
    val surfaceRequest by surfaceRequests.collectAsState(initial = null)

    var imageCapture by remember {
        mutableStateOf<ImageCapture?>(null)
    }

    var picture by remember {
        mutableStateOf<Uri?>(null)
    }

    var cameraConfig by remember {
        mutableStateOf(defaultCameraConfig)
    }

    val executor = remember {
        Executors.newSingleThreadExecutor()
    }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    DisposableEffect(Unit) {
        onDispose { executor.shutdown() }
    }

    LaunchedEffect(cameraConfig) {
        val captureMode = if (cameraConfig.captureMode is CaptureMode.Quality) {
            CAPTURE_MODE_MAXIMIZE_QUALITY
        } else {
            CAPTURE_MODE_MINIMIZE_LATENCY
        }

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(captureMode)
            .setTargetRotation(view.display.rotation)
            .build()
    }

    LaunchedEffect(Unit) {
        val provider = ProcessCameraProvider.awaitInstance(context = context)
        val preview = CameraPreview.Builder()
            .build()
            .apply {
                setSurfaceProvider { request ->
                    surfaceRequests.value = request
                }
            }

        provider.unbindAll()
        provider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview, imageCapture
        )
    }

    LaunchedEffect(picture) {
        imageCapture?.let { bottomSheetState.show() }
    }

    CameraScaffold(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            surfaceRequest?.let {
                CameraXViewfinder(surfaceRequest = it, modifier = Modifier.fillMaxSize())
            }
            CircleButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                onClick = {
                    val imageCapture = imageCapture ?: return@CircleButton

                    takePicture(
                        context,
                        imageCapture = imageCapture,
                        executor,
                        onImageCaptured = { savedUri ->
                            picture = savedUri.savedUri
                        }
                    )

                }
            )
        }
    }

    ImagePreviewBottomSheet(
        state = bottomSheetState,
        onDismiss = {
            picture = null
            coroutineScope.launch {
                bottomSheetState.hide()
            }
        },
        content = picture ?: return
    )
}

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPress by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPress) 0.9f else 1f,
        label = "circle button scale"
    )

    IconButton(
        shape = CircleShape,
        interactionSource = interactionSource,
        colors = IconButtonColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray,
        ),
        modifier = modifier
            .border(4.dp, Color.White, CircleShape)
            .size(56.dp)
            .clip(CircleShape)
            .scale(scale)

            .padding(8.dp),
        onClick = onClick
    ) {}
}

fun takePicture(
    context: Context,
    imageCapture: ImageCapture,
    executor: ExecutorService,
    onImageCaptured: (outputFileResults: ImageCapture.OutputFileResults) -> Unit
) {
    val contentValue = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "image_${System.currentTimeMillis()}")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyCameraApp")
        }
    }

    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValue
        )
        .build()

    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onImageCaptured(outputFileResults)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Image capture failed:", exception.message, exception)
                Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
}

fun getCapturedImage(context: Context, uri: Uri): Bitmap {
    context.contentResolver.openInputStream(uri).use { stream ->
        return BitmapFactory.decodeStream(stream)
    }
}

@Preview
@Composable
private fun ImageCaptureScreenPreview() {
    CameraTheme {
        ImageCaptureScreen()
    }
}