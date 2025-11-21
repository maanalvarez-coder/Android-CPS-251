package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import org.json.JSONObject

/**
 * OCR REVIEW SCREEN
 * ------------------------------------------------
 * • Accepts encoded merged JSON string from OCR screen
 * • Decodes and parses important fields
 * • Lets user edit merchant/date/subtotal/tax/total
 * • Displays line items list
 * • "Confirm" moves to Home and later will insert into DB
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OCRReview(
    encodedJson: String?,     // Encoded receipt JSON passed through navigation
    onBackClick: () -> Unit,  // Go back to OCR screen
    onConfirm: () -> Unit     // Confirm and go to Home (DB later)
) {
    // --------------------------------------------
    // Decode JSON from the nav route
    // --------------------------------------------
    val rawJson = remember(encodedJson) {
        encodedJson?.let {
            try {
                URLDecoder.decode(it, StandardCharsets.UTF_8)
            } catch (_: Exception) {
                null
            }
        }
    }

    // Parse object: Tabscanner wraps everything inside "result"
    val resultObject = remember(rawJson) {
        if (rawJson != null) {
            try {
                val root = JSONObject(rawJson)
                root.optJSONObject("result") ?: root   // fallback if no wrapper
            } catch (_: Exception) {
                null
            }
        } else null
    }

    // Editable fields
    var merchantName by remember {
        mutableStateOf(resultObject?.optString("establishment") ?: "")
    }
    var receiptDate by remember {
        mutableStateOf(resultObject?.optString("date") ?: "")
    }
    var subtotal by remember {
        mutableStateOf(resultObject?.optDouble("subTotal", 0.0).takeIf { it != 0.0 }?.toString() ?: "")
    }
    var tax by remember {
        mutableStateOf(resultObject?.optDouble("tax", 0.0).takeIf { it != 0.0 }?.toString() ?: "")
    }
    var total by remember {
        mutableStateOf(resultObject?.optDouble("total", 0.0).takeIf { it != 0.0 }?.toString() ?: "")
    }

    // ----------------------------
    // Line items displayed as text
    // ----------------------------
    val lineItemsText = remember(resultObject) {
        val items = resultObject?.optJSONArray("lineItems")
        if (items != null && items.length() > 0) {
            buildString {
                for (i in 0 until items.length()) {
                    val item = items.getJSONObject(i)
                    val name = item.optString("descClean", item.optString("desc", "Item"))
                    val price = item.optDouble("lineTotal", 0.0)
                    append("- $name: $price\n")
                }
            }
        } else ""
    }

    // --------------------------------------------
    // UI
    // --------------------------------------------
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirm Receipt") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("< Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text("Review and edit the scanned details before saving:",
                modifier = Modifier.padding(bottom = 16.dp))

            // Editable fields
            OutlinedTextField(
                value = merchantName,
                onValueChange = { merchantName = it },
                label = { Text("Merchant") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = receiptDate,
                onValueChange = { receiptDate = it },
                label = { Text("Date/Time") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = subtotal,
                onValueChange = { subtotal = it },
                label = { Text("Subtotal") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = tax,
                onValueChange = { tax = it },
                label = { Text("Tax") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = total,
                onValueChange = { total = it },
                label = { Text("Total") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // -------------------------
            // Show line items
            // -------------------------
            if (lineItemsText.isNotBlank()) {
                Text("Line Items:", style = MaterialTheme.typography.titleMedium)
                Text(lineItemsText, modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(Modifier.height(24.dp))

            // CONFIRM BUTTON
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm")
            }

            Spacer(Modifier.height(16.dp))

            // Optional debug output
            rawJson?.let {
                Text("Raw merged JSON (debug):", modifier = Modifier.padding(top = 8.dp))
                Text(it)
            }
        }
    }
}
