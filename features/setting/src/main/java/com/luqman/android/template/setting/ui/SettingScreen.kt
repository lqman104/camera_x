package com.luqman.android.template.setting.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.luqman.android.template.core.ui.theme.AndroidMVITemplateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    modifier: Modifier,
    onBack: () -> Unit = {}
) {
    Scaffold(modifier, topBar = {
        TopAppBar(title = { Text("Setting") }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(com.luqman.android.template.core.ui.R.drawable.ic_arrow_back),
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
            Text("Setting page")
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPrev() {
    AndroidMVITemplateTheme {
        SettingScreen(Modifier.fillMaxSize())
    }
}