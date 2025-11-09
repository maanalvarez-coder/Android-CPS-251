package com.example.assignment9.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment9.MovieDetails
import com.example.assignment9.MovieSearchScreen
import com.example.assignment9.MovieViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SEARCH
    ) {
        composable(NavRoutes.SEARCH) {
            MovieSearchScreen(
                onDetailsClick = { navController.navigate(NavRoutes.DETAILS) },
            )
    }
        composable(NavRoutes.DETAILS){
            MovieDetails(
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}