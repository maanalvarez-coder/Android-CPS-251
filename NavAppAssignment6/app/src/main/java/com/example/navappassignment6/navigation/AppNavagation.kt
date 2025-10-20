package com.example.navappassignment6.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navappassignment6.modelViews.LoginView
import com.example.navappassignment6.modelViews.SharedView
import com.example.navappassignment6.screens.HomeScreen
import com.example.navappassignment6.screens.LoginScreen
import com.example.navappassignment6.screens.ProfileScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val sharedView: SharedView = viewModel()
    val loginView: LoginView = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN
    ) {
        composable(NavRoutes.LOGIN){

            LoginScreen(
                loginView = loginView,
                sharedView = sharedView,
                onHomeClick = { userName -> navController.navigate(NavRoutes.HOME)}
                )
        }
        composable(NavRoutes.HOME){
            HomeScreen(
                sharedView = sharedView,
                onProfileClick = {navController.navigate(NavRoutes.PROFILE)},
                onLogoutClick = {navController.navigate(NavRoutes.LOGIN){popUpTo(0){inclusive = true} } }
            )

        }
        composable(NavRoutes.PROFILE){
            ProfileScreen(
                sharedView = sharedView,
                onBackClick = {navController.popBackStack()}
            )
        }
    }


}

