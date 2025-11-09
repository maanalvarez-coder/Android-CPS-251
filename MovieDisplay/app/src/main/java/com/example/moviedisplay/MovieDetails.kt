package com.example.moviedisplay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment9.api.MovieDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    imdbId: String,
    movieViewModel: MovieViewModel,
    onBackClick: () -> Unit
) {
    var movieDetails by remember { mutableStateOf<MovieDetails?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Fetch movie details when the screen is launched
    LaunchedEffect(imdbId) {
        isLoading = true
        error = null
        val result = movieViewModel.getMovieDetailsById(imdbId)
        if (result != null) {
            movieDetails = result
        } else {
            error = "Failed to load movie details."
        }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movieDetails?.Title ?: "Movie Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Card(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            shape = RoundedCornerShape(8.dp),
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
            } else {
                movieDetails?.let { movie ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = movie.Poster,
                            contentDescription = "Movie Poster",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        )

                        Text(text = "Year: ${movie.Year}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Rated: ${movie.Rated}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Director: ${movie.Director}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Actors: ${movie.Actors}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Rotten Tomatoes: ${movie.Ratings?.firstOrNull { it.Source == "Rotten Tomatoes" }?.Value ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Box Office: ${movie.BoxOffice}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "IMDB Rating: ${movie.imdbRating}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Plot: ${movie.Plot}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
