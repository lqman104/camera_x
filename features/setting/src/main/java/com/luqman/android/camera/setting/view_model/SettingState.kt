package com.luqman.android.camera.setting.view_model

data class SettingState(
    val name: String? = null,
    val title: String? = null,
    val saved: Boolean = false
)

sealed interface SettingEvent {
    data class OnNameChange(val value: String) : SettingEvent
    data class OnTitleChange(val value: String) : SettingEvent
    data object OnSave : SettingEvent
}