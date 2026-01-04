package com.luqman.android.template.dashboard.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.OnNoteChange -> onNoteChange(event.value)
        }
    }

    private fun onNoteChange(value: String) {
        _state.update {
            it.copy(notes = value)
        }
    }

}