package com.luqman.android.camera.setting.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luqman.android.camera.core.ui.theme.CameraTheme
import com.luqman.android.camera.setting.view_model.SettingEvent
import com.luqman.android.camera.setting.view_model.SettingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    modifier: Modifier,
    state: SettingState = SettingState(),
    notes: String = "Setting page",
    onEvent: (SettingEvent) -> Unit = {},
    onBack: () -> Unit = {}
) {
    LaunchedEffect(state.saved) {
        if (state.saved) {
            onBack()
        }
    }

    Scaffold(modifier, topBar = {
        TopAppBar(title = { Text("Setting") }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(com.luqman.android.camera.core.ui.R.drawable.ic_arrow_back),
                    contentDescription = "icon back"
                )
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
                value = state.name.orEmpty(),
                onValueChange = {
                    onEvent(SettingEvent.OnNameChange(it))
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                value = state.title.orEmpty(),
                onValueChange = {
                    onEvent(SettingEvent.OnTitleChange(it))
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                onClick = {
                    onEvent(SettingEvent.OnSave)
                }
            ) {
                Text("Save")
            }

            Text(notes.ifEmpty { "Setting page" })
        }
    }
}

@Preview
@Composable
private fun SettingScreenPrev() {
    CameraTheme {
        SettingScreen(Modifier.fillMaxSize())
    }
}