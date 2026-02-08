package com.luqman.android.camera.setting.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.android.camera.setting.use_case.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SettingState> = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.OnNameChange -> onNameChange(event.value)
            is SettingEvent.OnTitleChange -> onTitleChange(event.value)
            is SettingEvent.OnSave -> save()
        }
    }

    private fun onNameChange(value: String) {
        _state.update { state ->
            state.copy(name = value)
        }
    }

    private fun onTitleChange(value: String) {
        _state.update { state ->
            state.copy(title = value)
        }
    }

    private fun save() {
        viewModelScope.launch {
            try {
                saveUserUseCase(
                    name = state.value.name.orEmpty(),
                    title = state.value.title.orEmpty()
                )
                _state.update { state ->
                    state.copy(saved = true)
                }
            } catch (e: Exception) {
                Log.e("save: ", e.message, e)
            }
        }
    }
}