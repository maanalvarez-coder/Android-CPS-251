package com.example.budgetapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.budgetapplication.screens.HomeScreen
import com.example.budgetapplication.screens.InputSpending
import com.example.budgetapplication.screens.LoginScreen
import com.example.budgetapplication.screens.ProfileScreen
import com.example.budgetapplication.screens.Splash
import com.example.budgetapplication.screens.ViewTransactions
import com.example.budgetapplication.views.LoginView
import com.example.budgetapplication.views.SharedView
import kotlinx.coroutines.delay

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val sharedView: SharedView = viewModel()
    val loginView: LoginView = viewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASH
    ) {
        composable(NavRoutes.SPLASH){
            LaunchedEffect(Unit) {
                delay(5000)
                navController.navigate(NavRoutes.LOGIN)
            }
            Splash()
        }
        composable(NavRoutes.LOGIN){

            LoginScreen(
                loginView = loginView,
                sharedView = sharedView,
                onHomeClick = { userName -> navController.navigate(NavRoutes.HOME) },
                onInputSpendingClick = {},
                onViewTransactionClick = {}
            )
        }
        composable(NavRoutes.HOME){
            HomeScreen(
                sharedView = sharedView,
                onProfileClick = { navController.navigate(NavRoutes.PROFILE) },
                onLogoutClick = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                onHomeClick = {userName -> navController.navigate(NavRoutes.HOME)},
                onInputSpendingClick = {navController.navigate(NavRoutes.INPUT)},
                onViewTransactionClick = {navController.navigate(NavRoutes.TRANSACTION)}
            )

        }
        composable(NavRoutes.PROFILE){
            ProfileScreen(
                sharedView = sharedView,
                onBackClick = { navController.popBackStack() },
                onProfileClick = {navController.navigate(NavRoutes.PROFILE)} ,
                onHomeClick = {userName -> navController.navigate(NavRoutes.HOME)},
                onInputSpendingClick = {navController.navigate(NavRoutes.INPUT)},
                onViewTransactionClick = {navController.navigate(NavRoutes.TRANSACTION)}
            )
        }
        composable(NavRoutes.INPUT){
            InputSpending(
                sharedView = sharedView,
                onBackClick = { navController.popBackStack() },
                onProfileClick = {navController.navigate(NavRoutes.PROFILE)} ,
                onHomeClick = {userName -> navController.navigate(NavRoutes.HOME)},
                onInputSpendingClick = {navController.navigate(NavRoutes.INPUT)},
                onViewTransactionClick = {navController.navigate(NavRoutes.TRANSACTION)}
            )
        }
        composable(NavRoutes.TRANSACTION){
            ViewTransactions(
                sharedView = sharedView,
                onBackClick = { navController.popBackStack() },
                onProfileClick = {navController.navigate(NavRoutes.PROFILE)} ,
                onHomeClick = {userName -> navController.navigate(NavRoutes.HOME)},
                onInputSpendingClick = {navController.navigate(NavRoutes.INPUT)},
                onViewTransactionClick = {navController.navigate(NavRoutes.TRANSACTION)}
            )
        }
    }


}