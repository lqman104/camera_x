package com.luqman.android.camera.dashboard.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.android.camera.dashboard.use_case.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserUseCase: GetUsersUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.GetData -> onGetData()
            is DashboardEvent.OnNoteChange -> onNoteChange(event.value)
        }
    }

    private fun onGetData() {
        viewModelScope.launch {
            val users = getUserUseCase()
            _state.update { state ->
                state.copy(users = users)
            }
        }
    }

    private fun onNoteChange(value: String) {
        _state.update {
            it.copy(notes = value)
        }
    }
}