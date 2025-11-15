package com.example.databasedressup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF03DAC6),
    onPrimaryContainer = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White,
    secondary =Color(0xFF03DAC6),
    onSecondary = Color.Black,
    surface = Color(0xFF1E1E1E) ,
    onSurface = Color.White,
    error = Color(0xFFCF6679) ,
    onError = Color.Black,
)
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color. White,
    primaryContainer = Color(0xFFBB86FC),
    onPrimaryContainer = Color.Black,
    background = Color(0xFFF0F0F0),
    onBackground = Color.Black,

    secondary=Color(0xFF03DAC6),
    onSecondary = Color.Black,
    surface = Color.White ,
    onSurface = Color.Black,
    error = Color(0xFFB00020) ,
    onError = Color.White,
)

// MainActivity is the entry point of the Android application.
class MainActivity : ComponentActivity() {
    // onCreate is called when the activity is first created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure the window to use light status bar icons for a modern look.
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // Initialize the ViewModel using by viewModels.
        // The provideFactory method ensures the ViewModel has access to the application context.
        val viewModel: NoteViewModel by viewModels {
            NoteViewModel.provideFactory(application)
        }

        // Set up the Compose UI content for this activity.
        setContent {
            // Apply the custom theme for the application.
            MaterialTheme(colorScheme = LightColorScheme) {
                // Surface is a fundamental composable that applies background color and fills the screen.
                lightColorScheme()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Display the NoteScreen, passing the initialized ViewModel to it.
                    NoteScreen(viewModel)
                }
            }
        }
    }
}