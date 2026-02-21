package com.luqman.android.camera.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luqman.android.camera.core.ui.theme.CameraTheme

data class Menu(
    val key: String,
    val name: String,
    val action: () -> Unit
)

@Composable
fun DashboardScreen(
    modifier: Modifier,
    goToImageCapture: () -> Unit = {},
    goToCameraBasic: () -> Unit = {}
) {
    val menus = buildList {
        add(
            Menu(
                key = "basic",
                name = "Basic Camera",
                action = goToCameraBasic
            )
        )

        add(
            Menu(
                key = "image capture",
                name = "Image Capture",
                action = goToImageCapture
            )
        )
    }

    Scaffold(modifier) { paddingValues ->
        LazyRow(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(4.dp),
        ) {
            items(items = menus, key = { it.key }) {
                Button(
                    modifier = Modifier.padding(PaddingValues(4.dp)),
                    onClick = it.action
                ) {
                    Text(it.name)
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