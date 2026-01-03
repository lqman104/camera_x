package com.luqman.android.template.mvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.luqman.android.template.core.ui.theme.AndroidMVITemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidMVITemplateTheme {
                MainNavigation(Modifier)
            }
        }
    }
}
