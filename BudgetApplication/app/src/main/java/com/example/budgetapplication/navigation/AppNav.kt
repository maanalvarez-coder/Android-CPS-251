package com.example.budgetapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetapplication.screens.HomeScreen
import com.example.budgetapplication.screens.LoginScreen
import com.example.budgetapplication.screens.ProfileScreen
import com.example.budgetapplication.views.LoginView
import com.example.budgetapplication.views.SharedView

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