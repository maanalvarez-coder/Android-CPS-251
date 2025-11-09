package com.example.moviedisplay.api

import com.example.assignment9.api.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(".")
    suspend fun getMovies(
        @Query("s") search: String,
        @Query("apikey") apikey: String,
    ): MovieResponse


    @GET(".")
    suspend fun getMovie(
        @Query("i") id: String,
        @Query("apikey") apikey: String,
    ): MovieDetails
}