package com.luqman.android.camera.dashboard.view_model

import com.luqman.android.camera.repositories.models.User

data class DashboardState(
    val users: List<User>? = null,
    val notes: String = "",
)

sealed interface DashboardEvent {
    data object GetData: DashboardEvent
    data class OnNoteChange(val value: String): DashboardEvent
}
