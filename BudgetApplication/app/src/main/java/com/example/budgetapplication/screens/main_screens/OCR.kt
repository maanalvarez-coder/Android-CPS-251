git package com.example.budgetapplication.screens.main_screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

/**
 * OCR SCREEN
 * ---------------------------------------------
 * This screen handles:
 *  • Selecting multiple images from gallery
 *  • Taking photos with camera
 *  • Sending each image to Tabscanner
 *  • Collecting all responses and merging them
 *  • Navigating to the Review screen with merged receipt JSON
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OCR(
    onNavigateToReview: (String) -> Unit   // Callback to go to review screen with JSON string
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Stores selected receipt photos (one or many)
    val selectedImageUris = remember { mutableStateListOf<Uri>() }

    // Show preview for the first selected image
    var firstPreviewBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // UI state
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Your Tabscanner API key
    val TABSCANNER_API_KEY = "QVEPMoB793uqSBQ4Lic9eXr9UdyJd2YSEKCb4EDnWNjjh7BOASXwYzsRh6WEsB6s"

    // ---------------------------
    // GALLERY PICKER (multiple)
    // ---------------------------
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (!uris.isNullOrEmpty()) {
                selectedImageUris.clear()
                selectedImageUris.addAll(uris)

                // Only show preview of the first image
                firstPreviewBitmap = loadBitmapFromUri(context, uris.first())
            }
        }

    // ---------------------------
    // CAMERA PICKER (one at a time)
    // ---------------------------
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Camera returns a small Bitmap thumbnail
                val bmp = result.data?.extras?.get("data") as? Bitmap
                if (bmp != null) {
                    val file = bitmapToFile(context, bmp)
                    val uri = Uri.fromFile(file)

                    selectedImageUris.add(uri)

                    // If no previous image, use this as preview
                    if (firstPreviewBitmap == null) firstPreviewBitmap = bmp
                }
            }
        }

    // ---------------------------
    // UI Layout
    // ---------------------------
    Scaffold(
        topBar = { TopAppBar(title = { Text("Scan Receipt") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ----------- Buttons (Gallery / Camera) -----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text("Add from Gallery")
                }

                Button(
                    onClick = {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        cameraLauncher.launch(intent)
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text("Add Photo")
                }
            }

            Spacer(Modifier.height(16.dp))

            // ----------- Image Preview -----------
            if (selectedImageUris.isNotEmpty()) {
                Text("${selectedImageUris.size} image(s) selected")

                Spacer(Modifier.height(12.dp))

                firstPreviewBitmap?.let { bmp ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        elevation = elevatedCardElevation(4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Image(
                            bitmap = bmp.asImageBitmap(),
                            contentDescription = "Receipt preview",
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                        )
                    }
                }
            } else {
                Text("No images selected yet.")
            }

            Spacer(Modifier.height(24.dp))

            // ----------- Process Button -----------
            Button(
                onClick = {
                    if (selectedImageUris.isEmpty()) {
                        errorMessage = "Please add at least one image."
                        return@Button
                    }

                    // Launch OCR processing
                    scope.launch {
                        isLoading = true
                        errorMessage = null

                        // Convert all Uris → Files
                        val imageFiles = selectedImageUris.map { uriToFile(context, it) }

                        // Send each file to Tabscanner
                        val rawResults = mutableListOf<String>()
                        for (file in imageFiles) {
                            val sessionId = uploadToTabscanner(file, TABSCANNER_API_KEY)
                            if (sessionId != null) {
                                val json = pollTabscanner(sessionId, TABSCANNER_API_KEY)
                                if (json != null) rawResults.add(json)
                            }
                        }

                        isLoading = false

                        if (rawResults.isEmpty()) {
                            errorMessage = "Failed to process receipt images."
                        } else {
                            // Merge all pages into one
                            val mergedJson = mergeTabscannerResults(rawResults)

                            // Encode for navigation
                            val encoded = URLEncoder.encode(
                                mergedJson,
                                StandardCharsets.UTF_8.toString()
                            )

                            onNavigateToReview(encoded)
                        }
                    }
                },
                enabled = selectedImageUris.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Process Receipt")
            }

            Spacer(Modifier.height(16.dp))

            // Loading bar
            if (isLoading) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
                Text("Processing with Tabscanner…")
            }

            // Error messages
            errorMessage?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = Color.Red)
            }
        }
    }
}

/* ============================================================================
   Helper Functions (Image conversion, file handling, API calls, merging results)
   STUDENT-FRIENDLY: These functions are NOT UI. They perform the "work".
   ============================================================================ */

/** Load a Bitmap from a Uri (for preview only). */
private fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? =
    context.contentResolver.openInputStream(uri)?.use(BitmapFactory::decodeStream)

/** Convert Uri → File (needed because Tabscanner only accepts actual files). */
private fun uriToFile(context: Context, uri: Uri): File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val tempFile = File(context.cacheDir, "ocr_${System.currentTimeMillis()}.jpg")
    FileOutputStream(tempFile).use { outputStream ->
        inputStream?.copyTo(outputStream)
    }
    return tempFile
}

/** Convert camera Bitmap → File. */
private fun bitmapToFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "camera_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out)
    }
    return file
}

/**
 * Upload one image to Tabscanner.
 * Returns: The session token used later for polling.
 */
private suspend fun uploadToTabscanner(image: File, apiKey: String): String? =
    withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    image.name,
                    RequestBody.create("image/jpeg".toMediaTypeOrNull(), image)
                )
                .build()

            val request = Request.Builder()
                .url("https://api.tabscanner.com/api/2/process")
                .addHeader("apikey", apiKey)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val resultString = response.body?.string()

            val json = resultString?.let { JSONObject(it) } ?: return@withContext null

            // Tabscanner may return "token" or "id"
            json.optString("token")
                .ifEmpty { json.optString("id", null) }
        } catch (e: Exception) {
            null
        }
    }

/**
 * Poll Tabscanner after processing begins.
 * Returns: The extracted JSON result for this one page.
 */
private suspend fun pollTabscanner(sessionId: String, apiKey: String): String? =
    withContext(Dispatchers.IO) {
        try {
            Thread.sleep(8000) // Give Tabscanner time to finish

            val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            val request = Request.Builder()
                .url("https://api.tabscanner.com/api/result/$sessionId")
                .addHeader("apikey", apiKey)
                .get()
                .build()

            client.newCall(request).execute().body?.string()
        } catch (e: Exception) {
            null
        }
    }

/**
 * Merge results of multiple uploaded receipt images.
 * This allows LONG receipts to be scanned page by page.
 */
private fun mergeTabscannerResults(rawResults: List<String>): String {
    var merchant: String? = null
    var date: String? = null
    var subtotalSum = 0.0
    var taxSum = 0.0
    var totalSum = 0.0
    val mergedLineItems = JSONArray()

    for (raw in rawResults) {
        val root = JSONObject(raw)
        val result = root.optJSONObject("result") ?: continue

        if (merchant.isNullOrBlank())
            merchant = result.optString("establishment", null)

        if (date.isNullOrBlank())
            date = result.optString("date", null)

        subtotalSum += result.optDouble("subTotal", 0.0)
        taxSum += result.optDouble("tax", 0.0)
        totalSum += result.optDouble("total", 0.0)

        val items = result.optJSONArray("lineItems")
        if (items != null) {
            for (i in 0 until items.length()) {
                mergedLineItems.put(items.getJSONObject(i))
            }
        }
    }

    // Build merged JSON structure
    val mergedResult = JSONObject().apply {
        put("establishment", merchant ?: "")
        put("date", date ?: "")
        put("subTotal", subtotalSum)
        put("tax", taxSum)
        put("total", totalSum)
        put("lineItems", mergedLineItems)
    }

    return JSONObject().apply {
        put("message", "MERGED_SUCCESS")
        put("status", "done")
        put("status_code", 3)
        put("result", mergedResult)
    }.toString()
}
