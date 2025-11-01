package com.example.assignment8

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ContactScreen(viewModel: ContactViewModel) {

    val contacts by viewModel.allContacts.collectAsState(emptyList())
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var nameSearch by remember { mutableStateOf("") }
    var wasBlank by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf<Contact?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        // Main column for arranging input fields, buttons, and the note list.
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 50.dp)
        ) {
            // Title for the note input section.
            Text("Add a Contact", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            // Outlined text field for note title input.
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                supportingText = {if(name.isBlank() && wasBlank)Text("Name can't be empty")},
                isError = name.isBlank() && wasBlank,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            // Outlined text field for note content input.
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it
                                wasBlank = false},
                label = { Text("Phone Number (10 digits)") },
                supportingText = { if(phone.isNotBlank() &&!viewModel.isValidPhoneNumber(phone)){Text("Invalid phone number format please enter like this 999.999.9999")} },
                isError =  phone.isNotBlank() &&!viewModel.isValidPhoneNumber(phone),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            // Row for action buttons (Add/Update Note, Cancel Edit).
            Row {
                // Button to add a new note or update an existing one.
                Button(onClick = {
                    if(name.isBlank())
                        wasBlank = true
                    if (name.isNotBlank() && phone.isNotBlank() && viewModel.isValidPhoneNumber(
                            phoneNumber = phone
                        )
                    ) {
                        viewModel.insert(Contact(name = name, number = phone))
                        scope.launch { snackbarHostState.showSnackbar("Contact added!") }
                        name = ""
                        phone = ""
                    }
                }) { Text("Add Contact") }

                Button(onClick = {
                    viewModel.onSortOrderChange(SortOrder.DESC)
                    }
                ) { Text("DESC") }

                Button(onClick = {
                     viewModel.onSortOrderChange(SortOrder.ASC)
                }) { Text("ASC") }


            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = nameSearch,
                onValueChange = { nameSearch = it
                    viewModel.onSearchQueryChange(it)},
                label = { Text("Search Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) { Text("Contacts:", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center) }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(contacts.size) { idx ->
                    val contact = contacts[idx]
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        tonalElevation = 2.dp
                    ) {
                        // Row to arrange note details and action buttons horizontally.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Column for displaying Contact
                            Column(modifier = Modifier.weight(1f)) {
                                Text(contact.name, style = MaterialTheme.typography.titleSmall)
                                Text(contact.number, style = MaterialTheme.typography.bodyMedium)
                            }
                            IconButton(onClick = {showDeleteDialog = contact},) { Icon(imageVector = Icons.Default.Delete, contentDescription = "delete contact") }
                        }
                    }
                    if (showDeleteDialog != null) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = null },
                            title = { Text("Delete Contact") },
                            text = { Text("Are you sure you want to delete this Contact?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    // Delete the note, show snackbar, and reset states.
                                    viewModel.delete(showDeleteDialog!!)
                                    scope.launch { snackbarHostState.showSnackbar("Contact deleted!") }
                                    showDeleteDialog = null
                                    // If the deleted note was being edited, clear editing state.
                                }) {
                                    Text("Delete")
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
        }
    }
}

