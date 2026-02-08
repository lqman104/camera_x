package com.luqman.android.camera.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luqman.android.camera.core.ui.theme.CameraTheme

@Composable
fun DashboardScreen(
    modifier: Modifier,
    goToCameraBasic: () -> Unit = {}
) {
    Scaffold(modifier) { paddingValues ->
        LazyRow(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            item(key = "basic") {
                Button(
                    onClick = goToCameraBasic
                ) {
                    Text("Basic Camera")
                }
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
        )
    }
}