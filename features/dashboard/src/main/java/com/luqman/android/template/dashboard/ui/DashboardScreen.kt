package com.luqman.android.template.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.luqman.android.template.core.ui.theme.AndroidMVITemplateTheme

@Composable
fun DashboardScreen(
    modifier: Modifier,
    goToSetting: () -> Unit = {}
) {
    Scaffold(modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("testing")
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
    AndroidMVITemplateTheme {
        DashboardScreen(Modifier.fillMaxSize())
    }
}