package com.luqman.android.camera.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luqman.android.camera.core.ui.theme.CameraTheme
import com.luqman.android.camera.dashboard.view_model.DashboardEvent
import com.luqman.android.camera.dashboard.view_model.DashboardState

@Composable
fun DashboardScreen(
    modifier: Modifier,
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit = {},
    goToSetting: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        onEvent(DashboardEvent.GetData)
    }

    Scaffold(modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.users.orEmpty()) { user ->
                    Text(user.name)
                    Text(user.email)
                }
            }

            Button(
                onClick = goToSetting
            ) {
                Text("Navigate to setting")
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPrev() {
    CameraTheme {
        DashboardScreen(
            modifier = Modifier.fillMaxSize(),
            state = DashboardState()
        )
    }
}