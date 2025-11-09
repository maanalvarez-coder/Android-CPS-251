package com.example.moviedisplay.api

data class MovieSearchItem(
    val Title: String?,
    val Year: String?,
    val imdbID: String?,
    val Type: String?,
    val Poster: String?
)

data class MovieResponse(
    val Search: List<MovieSearchItem>?,
    val totalResults: String?,
    val Response: String?,
    val Error: String? = null
)

