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
import com.luqman.android.camera.dashboard.ui.DashboardScreen
import com.luqman.android.camera.features_navigation.BasicCameraNav
import com.luqman.android.camera.features_navigation.ImageCaptureNav
import com.luqman.android.camera.features_navigation.MainMenuNav
import com.luqman.android.camera.image_capture.ui.ImageCaptureScreen
import com.luqman.android.camera.setting.model.SettingNav
import com.luqman.android.camera.setting.ui.SettingScreen
import com.luqman.android.camera.setting.view_model.SettingViewModel

@Composable
fun MainNavigation(
    modifier: Modifier,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, modifier = modifier, startDestination = MainMenuNav) {
        composable<MainMenuNav> {
            DashboardScreen(
                modifier = Modifier,
                goToCameraBasic = { navController.navigate(BasicCameraNav) },
                goToImageCapture = { navController.navigate(ImageCaptureNav) }
            )
        }
        composable<BasicCameraNav> {
            BasicCameraScreen()
        }
        composable<ImageCaptureNav> {
            ImageCaptureScreen()
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