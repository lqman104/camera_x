package com.luqman.android.template.mvi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luqman.android.template.dashboard.ui.DashboardScreen
import com.luqman.android.template.setting.ui.SettingScreen

@Composable
fun MainNavigation(
    modifier: Modifier,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, modifier = modifier, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                Modifier,
                goToSetting = { navController.navigate("setting") }
            )
        }
        composable("setting") {
            SettingScreen(
                Modifier,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}