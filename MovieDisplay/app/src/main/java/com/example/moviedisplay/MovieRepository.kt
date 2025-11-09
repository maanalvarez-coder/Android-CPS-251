package com.example.moviedisplay

import com.example.assignment9.api.MovieDetails
import com.example.moviedisplay.api.ApiService
import com.example.moviedisplay.api.MovieResponse


class MovieRepository ( private val apiService: ApiService) {

    private val API_KEY = "3d0ef0f5"

    suspend fun getMovies(title: String): Result<MovieResponse> {
        return try {
            val response = apiService.getMovies(
                apikey = API_KEY,
                search = title
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getMovie(id: String): Result<MovieDetails>{
        return try {
            val response = apiService.getMovie(
                apikey = API_KEY,
                id = id
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}