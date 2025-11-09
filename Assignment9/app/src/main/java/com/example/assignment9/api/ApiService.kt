package com.example.assignment9.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    suspend fun getMovies(
        @Query("t") title: String,
        @Query("apikey") apikey: String,
    ): MovieResponse
}