package com.luqman.android.template.dashboard.view_model

data class DashboardState(
    val notes: String = "",
)

sealed interface DashboardEvent {
    data class OnNoteChange(val value: String): DashboardEvent
}
