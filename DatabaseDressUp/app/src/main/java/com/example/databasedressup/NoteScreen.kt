package com.example.databasedressup


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.getValue
import kotlin.time.Duration.Companion.milliseconds


// Opt-in to use experimental Material 3 API features, like the new Scaffold.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    // Collect the list of notes from the ViewModel as a State, so UI recomposes on changes.
    val notes by viewModel.notes.collectAsState()
    // State for the title input field.
    var title by remember { mutableStateOf("") }
    var titleTouched by remember { mutableStateOf(false) }
    // State for the content input field.
    var content by remember { mutableStateOf("") }
    var contentTouched by remember { mutableStateOf(false) }
    // State to hold the note currently being edited, if any.
    var editingNote by remember { mutableStateOf<Note?>(null) }
    // State to control the visibility of the delete confirmation dialog.
    var showDeleteDialog by remember { mutableStateOf<Note?>(null) }
    var expanded by remember { mutableStateOf(false) }
    // Formatter for displaying dates in a consistent format.
    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }
    // State for managing SnackBar messages.
    val snackbarHostState = remember { SnackbarHostState() }
    // Coroutine scope for launching suspending functions, like showing snackbars.
    val scope = rememberCoroutineScope()
    val cardElevation by animateDpAsState(
        targetValue = if (expanded) 26.dp else 4.dp,
        label = "cardElevation"
    )

    // Scaffold provides a basic screen layout with a SnackBarHost.
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { topBar() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        if (editingNote == null) {
                            // If not editing, add a new note.
                            viewModel.addNote(title, content, dateFormat.format(Date()))
                            scope.launch { snackbarHostState.showSnackbar("Note added!") }
                        } else {
                            // If editing,
                            viewModel.deleteNote(editingNote!!)
                            viewModel.addNote(title, content, dateFormat.format(Date()))
                            editingNote = null
                        }
                        // Clear input fields after action.
                        title = ""
                        content = ""
                        titleTouched = false
                        contentTouched = false
                    }
                },
                containerColor = Color(0xFF03DAC6),
                contentColor = Color.Black,
                shape = MaterialTheme.shapes.medium            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Note"
                )
            }
        }


    ) { padding ->
        // Main column for arranging input fields, buttons, and the note list.
        Column(modifier = Modifier.padding(padding)) {

            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape =  RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {

                    // Title for the note input section.
                    Text(
                        text = if (editingNote == null) "Add Note" else "Update Note",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(). padding( start= 140.dp, top = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Outlined text field for note title input.
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it
                                        titleTouched = true},
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth().padding(start=16.dp, end=16.dp),
                        isError = title.isBlank()&& titleTouched,
                        supportingText = {if(titleTouched && title.isBlank()){
                            Text("Content can not be blank.")
                        }}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Outlined text field for note content input.
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it
                            contentTouched = true },
                        label = { Text("Content") },
                        modifier = Modifier.fillMaxWidth().padding(start=16.dp, end=16.dp),
                        isError = contentTouched && content.isBlank(),
                        supportingText = {if(contentTouched && content.isBlank()){
                            Text("Content can not be blank.")
                        }},
                        minLines = 3
                    )


                Spacer(modifier = Modifier.height(16.dp))
                // Row for action buttons (Add/Update Note, Cancel Edit).
                Row {
                    // Button to add a new note or update an existing one.

                        Button(onClick = {
                            if (title.isNotBlank() && content.isNotBlank()) {
                                if (editingNote == null) {
                                    // If not editing, add a new note.
                                    viewModel.addNote(title, content, dateFormat.format(Date()))
                                    scope.launch { snackbarHostState.showSnackbar("Note added!") }
                                } else {
                                    // If editing,
                                    viewModel.deleteNote(editingNote!!)
                                    viewModel.addNote(title, content, dateFormat.format(Date()))
                                    editingNote = null
                                    scope.launch { snackbarHostState.showSnackbar("Note updated!") }
                                }
                                // Clear input fields after action.
                                title = ""
                                content = ""
                                titleTouched = false
                                contentTouched = false
                            }
                        }
                        , enabled = title.isNotBlank() && content.isNotBlank(), modifier = Modifier.padding(bottom = 16.dp, start = 40.dp)
                        ) {
                            // Button text changes based on whether a note is being edited.
                            Text(if (editingNote == null) "Add Note" else "Update Note")
                        }

                    // Show "Cancel Edit" button only when a note is being edited.
                    if (editingNote != null) {
                        Spacer(modifier = Modifier.width(50.dp))
                        OutlinedButton(onClick = {
                            // Clear editing state and input fields on cancel.
                            editingNote = null
                            title = ""
                            content = ""
                        }) {
                            Text("Cancel Edit")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Title for the list of notes.
            Text("Your Notes", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            // LazyColumn to efficiently display a scrollable list of notes.
            LazyColumn(modifier = Modifier.fillMaxHeight()
            , verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)) {
                items(notes.size) { idx ->
                    val note = notes[idx]
                    var favorite by remember { mutableStateOf(false) }
                    val color by animateColorAsState(
                    targetValue = if (favorite) Color(0xFFBB86FC) else MaterialTheme.colorScheme.surface,
                    animationSpec = tween(durationMillis = 300),
                    label = "cardElevation"
                    )
                    val cardElevation by animateDpAsState(
                        targetValue = if (favorite) 26.dp else 4.dp,
                        animationSpec = tween(durationMillis = 300),
                        label = "cardElevation"
                    )

                    // Surface for individual note display, with a slight elevation.

                    Card(colors = CardDefaults.cardColors(containerColor = color),
                        onClick = {
                        editingNote = note
                        title = note.title
                        content = note.content
                    }, elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
                        shape = RoundedCornerShape(12.dp)

                    ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp,),
                        tonalElevation = 2.dp,
                        color = color
                    ) {
                        // Row to arrange note details and action buttons horizontally.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Column for displaying note title, content, and date.
                            Column(modifier = Modifier.weight(1f)) {
                                Text(note.title, style = MaterialTheme.typography.titleSmall)
                                Text(note.content, style = MaterialTheme.typography.bodyMedium)
                            }
                            // Column for Edit and Delete buttons.
                            Column {
                            Text(note.date, style = MaterialTheme.typography.bodySmall)
                            Row {
                                Spacer(modifier = Modifier.height(4.dp))

                                IconButton(
                                    onClick = {favorite = !favorite},
                                    colors = IconButtonDefaults.iconButtonColors(
                                        //contentColor = {if (favorite){ Color(0xFF03DAC6)}}
                                    )
                                ) {
                                    if (favorite){
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Favorite",
                                            tint = Color(0xFF03DAC6)
                                        )
                                    }else{
                                        Icon(
                                            imageVector = Icons.Default.StarBorder,
                                            contentDescription = "Favorite",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Button to initiate editing of a note.
                                Spacer(modifier = Modifier.width( 8.dp))
                                // Button to show delete confirmation dialog.
                                IconButton(
                                    onClick = { showDeleteDialog = note },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        contentColor = MaterialTheme.colorScheme.error
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete"
                                    )
                                }

                                }
                            }
                            }
                        }
                    }
                }
            }
        }
        // Delete confirmation AlertDialog.
        if (showDeleteDialog != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = null },
                title = { Text("Delete Note") },
                text = { Text("Are you sure you want to delete this note?") },
                confirmButton = {
                    TextButton(onClick = {
                        // Delete the note, show snackbar, and reset states.
                        viewModel.deleteNote(showDeleteDialog!!)
                        scope.launch { snackbarHostState.showSnackbar("Note deleted!") }
                        showDeleteDialog = null
                        // If the deleted note was being edited, clear editing state.
                        if (editingNote == showDeleteDialog) {
                            editingNote = null
                            title = ""
                            content = ""
                        }
                    },

                    ) {
                        Text(text="Delete", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar(){
    TopAppBar(
        title = {Text(text = "Material Note")},
         colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


