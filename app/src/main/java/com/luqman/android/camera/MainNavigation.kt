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
import com.luqman.android.camera.basic.ui.BasicCameraScreen
import com.luqman.android.camera.dashboard.model.BasicCameraNav
import com.luqman.android.camera.dashboard.model.DashboardNav
import com.luqman.android.camera.dashboard.ui.DashboardScreen
import com.luqman.android.camera.setting.model.SettingNav
import com.luqman.android.camera.setting.ui.SettingScreen
import com.luqman.android.camera.setting.view_model.SettingViewModel

@Composable
fun MainNavigation(
    modifier: Modifier,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, modifier = modifier, startDestination = DashboardNav) {
        composable<DashboardNav> {
            DashboardScreen(
                modifier = Modifier,
                goToCameraBasic = { navController.navigate(BasicCameraNav) }
            )
        }
        composable<BasicCameraNav> {
            BasicCameraScreen()
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