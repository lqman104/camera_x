package com.luqman.android.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.luqman.android.camera.dashboard.model.DashboardNav
import com.luqman.android.camera.dashboard.ui.DashboardScreen
import com.luqman.android.camera.dashboard.view_model.DashboardViewModel

import com.luqman.android.camera.setting.ui.SettingScreen
import com.luqman.android.camera.setting.view_model.SettingViewModel
import com.luqman.android.camera.setting.model.SettingNav

@Composable
fun MainNavigation(
    modifier: Modifier,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, modifier = modifier, startDestination = DashboardNav) {
        composable<DashboardNav> {
            val viewModel = hiltViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsState()
            DashboardScreen(
                state = state,
                onEvent = viewModel::onEvent,
                modifier = Modifier,
                goToSetting = { navController.navigate(SettingNav(state.notes)) }
            )
        }
        composable<SettingNav> {
            val settingNav = it.savedStateHandle.toRoute<SettingNav>()
            val viewModel = hiltViewModel<SettingViewModel>()
            val state by viewModel.state.collectAsState()
            SettingScreen(
                Modifier,
                notes = settingNav.notes,
                state = state,
                onEvent = viewModel::onEvent,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}