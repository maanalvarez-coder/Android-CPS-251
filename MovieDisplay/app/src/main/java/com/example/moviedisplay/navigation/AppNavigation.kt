package com.example.moviedisplay.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviedisplay.MovieDetailsScreen
import com.example.moviedisplay.MovieRepository
import com.example.moviedisplay.MovieSearchScreen
import com.example.moviedisplay.MovieViewModel
import com.example.moviedisplay.api.RetrofitClient


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val repository = remember { MovieRepository(RetrofitClient.api) }
    val factory = remember { MovieViewModel.provideFactory(repository) }
    val movieViewModel: MovieViewModel = viewModel(factory = factory)
    var details: String = ""
    NavHost(
        navController = navController,
        startDestination = NavRoutes.SEARCH
    ) {
        composable(NavRoutes.SEARCH) {
            MovieSearchScreen(
                onDetailsClick = {imdbId ->
                    navController.navigate("${NavRoutes.DETAILS}/${imdbId}") },
                movieViewModel = movieViewModel,

                )
        }

        composable("${NavRoutes.DETAILS}/{imdbId}") {
                backStackEntry -> val imdbId = backStackEntry.arguments?.getString("imdbId") ?: ""
            MovieDetailsScreen(
                imdbId= imdbId,
                onBackClick = {navController.popBackStack()} ,
                movieViewModel = movieViewModel,
            )
        }
    }
}