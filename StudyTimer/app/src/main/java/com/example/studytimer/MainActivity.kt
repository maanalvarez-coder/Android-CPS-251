package com.example.studytimer

import android.R
import android.R.attr.button
import android.graphics.Color.red
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlin.math.round

/**
 * This project covers concepts from Chapter 6 lessons:
 * - "Understanding State in Compose" - for state management and updates
 * - "Stateless and Stateful Composables" - for component design patterns
 * - "Launched Effect" - for side effects and timers
 *
 * Students should review these lessons before starting:
 * - Understanding State in Compose lesson for state management
 * - Stateless and Stateful Composables lesson for component patterns
 * - Launched Effect lesson for side effects and timers
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {

                StudyTimerApp()
            }
        }
    }
}

@Composable
fun StudyTimerApp() {
    // TODO: Create state variables for the timer
    // Hint: You need variables for:
    // - isRunning (boolean for timer state)
    var isRunning by remember { mutableStateOf(false) }
    // - timeRemaining (int for seconds remaining)
    var timeRemaining by remember { mutableStateOf(300) }
    // - sessionLength (int for total session time in minutes)
    var sessionLength by remember { mutableStateOf(5) }
    // - completedSessions (int for tracking completed sessions)
    var completedSessions by remember { mutableStateOf(0) }
    // Use remember and mutableStateOf for each
    // See "Understanding State in Compose" lesson for examples of state management

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(style = MaterialTheme.typography.titleLarge,text = "Study Timer")
        // TODO: Call the TimerDisplay composable here
        TimerDisplay(timeRemaining , sessionLength)
        // Pass the timeRemaining and sessionLength as parameters
        // See "Stateless and Stateful Composables" lesson for examples of stateless components

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Call the TimerControls composable here
        TimerControls(isRunning, onToggleTimer = {isRunning = !isRunning
        timeRemaining = sessionLength * 60})
        // Pass the isRunning state and a lambda to toggle the timer
        // See "Stateless and Stateful Composables" lesson for examples of stateful components

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Call the SessionSettings composable here
        SessionSettings(sessionLength, { minutes -> sessionLength = minutes
        timeRemaining = sessionLength * 60 ; isRunning = false})
        // Pass the sessionLength and a lambda to update it
        // See "Understanding State in Compose" lesson for examples of state updates

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Display the completed sessions count
        // Use Text composable with the completedSessions variable
        Text("Completed Sessions $completedSessions")
        // See "Understanding State in Compose" lesson for examples of displaying state
    }

    // TODO: Use LaunchedEffect to create the countdown timer
    // Hint: The effect should run when isRunning is true
    LaunchedEffect(isRunning){
        while (isRunning){
            if (timeRemaining == 0){ isRunning = false
            completedSessions++
            }
            delay(1000)
            timeRemaining--
        }
    }
    // Inside the effect, use delay(1000) and update timeRemaining
    // Don't forget to handle when the timer reaches zero!
    // See "Launched Effect" lesson for examples of side effects and timers
}

@Composable
fun TimerDisplay(
    timeRemaining: Int,
    sessionLength: Int
) {
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val progress = round(100 *(1f - (timeRemaining.toFloat() / (sessionLength*60))))
    // TODO: Create a stateless timer display component
    Text(style = MaterialTheme.typography.titleLarge, text = "%02d:%02d".format(minutes, seconds))
    Text(text = "$progress% completed")
    // Display the time remaining in MM:SS format
    // Use Text composables (no CircularProgressIndicator needed)
    // See "Stateless and Stateful Composables" lesson for examples of stateless components
    /* This code will show the percent completed
    val minutes = timeRemaining / 60
    val seconds = timeRemaining % 60
    val progress = 1f - (timeRemaining.toFloat() / (sessionLength * 60))*/
}

@Composable
fun TimerControls(
    isRunning: Boolean,
    onToggleTimer: () -> Unit
) {
    // TODO: Create stateful timer control button
    Button(onClick = onToggleTimer ) {
        if (!isRunning){
            Text(text = "Play")
        }
        else {
            Text(text= "Reset")
        }
    }
    // Show Play button when stopped, Reset button when running (works like a toggle)
    // Use Button composable with appropriate text
    // See "Stateless and Stateful Composables" lesson for examples of stateful components
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionSettings(
    sessionLength: Int,
    onSessionLengthChange: (Int) -> Unit

) {
    var selectedbutton: String? by remember { mutableStateOf(null)}
    val buttons = listOf("5","15","25","45")
    // TODO: Create session length configuration
    // Allow users to set custom session lengths (5, 15, 25, 45 minutes)

    FlowRow {
        buttons.forEach { button -> val selected = button == selectedbutton
            Button(
                onClick = {
                    onSessionLengthChange(button.toInt()); selectedbutton = button },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected) Color.White else Color.Magenta,
                    contentColor = if(selected) Color.Black else Color.White
                )) { Text("$button"+"m")}
        }


    }
    }





    // Use Button composables for each option
    // Highlight the currently selected length
    // Display the current session length value
    // Make sure the buttons show the correct minute values: 5m, 15m, 25m, 45m
    // Use appropriate button sizing (width = 70.dp) to display text properly
    // See "Understanding State in Compose" lesson for examples of state management and updates


@Preview(showBackground = true)
@Composable
fun StudyTimerPreview() {
    StudyTimerApp()
}
//What are the advantages of using remember and mutableStateOf for managing state in a Compose application, and how would you apply them to manage the isRunning and timeRemaining states in this project?
//What is the difference between stateless and stateful composables, and why is it important to keep TimerDisplay stateless in this project?
//How would you implement a countdown timer using LaunchedEffect in Compose, and what considerations should you make to ensure the timer stops when it reaches zero?
//Why is it beneficial to separate the TimerControls and SessionSettings into their own composables, and how does this improve the reusability and readability of the code?
//What is the role of LaunchedEffect in managing side effects in Compose, and how would you use it to ensure the timer updates every second only when isRunning is true?