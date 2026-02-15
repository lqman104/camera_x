package com.luqman.android.camera.image_capture.ui

sealed interface StorageMode {
    data object InMemory: StorageMode
    data object FileSave: StorageMode
}

sealed interface CaptureMode {
    data object Latency: CaptureMode
    data object Quality: CaptureMode
}

data class CameraConfig(
    val storageMode: StorageMode,
    val flashOn: Boolean,
    val captureMode: CaptureMode
)

val defaultCameraConfig = CameraConfig(
    storageMode = StorageMode.InMemory,
    flashOn = false,
    captureMode = CaptureMode.Latency
)