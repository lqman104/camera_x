package com.luqman.android.template.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.luqman.android.template.dashboard.model.DashboardNav
import com.luqman.android.template.dashboard.ui.DashboardScreen
import com.luqman.android.template.dashboard.view_model.DashboardViewModel
import com.luqman.android.template.setting.model.SettingNav
import com.luqman.android.template.setting.ui.SettingScreen

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
            SettingScreen(
                Modifier,
                notes = settingNav.notes,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}