package com.example.budgetapplication.views

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class OcrViewModel(application: Application) : AndroidViewModel(application) {

    var selectedImageUri: Uri? = null
    var capturedFile: File? = null
    var jsonResult: String? = null
    var isLoading = false
    var errorMessage: String? = null

    private val TABSCANNER_API_KEY = "kB2Ee2CmB0TVSdqNPdegBseflOCfTK1zqnszUEqBUIkBNvVsUIGBNqDYDHrVpW3C"

    // Convert URI → File
    fun uriToFile(uri: Uri): File {
        val resolver: ContentResolver = getApplication<Application>().contentResolver
        val inputStream = resolver.openInputStream(uri)
        val tempFile = File.createTempFile("receipt_", ".jpg", getApplication<Application>().cacheDir)
        FileOutputStream(tempFile).use { output ->
            inputStream?.copyTo(output)
        }
        return tempFile
    }

    // Upload to Tabscanner
    fun processWithTabscanner(file: File, onDone: () -> Unit) {
        isLoading = true
        errorMessage = null
        jsonResult = null

        viewModelScope.launch(Dispatchers.IO) {

            val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            val body = MultipartBody.Builder()
                .setType(MultipartBody.Companion.FORM)
                .addFormDataPart(
                    "file",
                    file.name,
                    RequestBody.Companion.create("image/jpeg".toMediaTypeOrNull(), file)
                )
                .build()

            val request = Request.Builder()
                .url("https://api.tabscanner.com/api/2/process")
                .addHeader("apikey",    TABSCANNER_API_KEY)
                .post(body)
                .build()

            try {
                val response = client.newCall(request).execute()
                val bodyString = response.body?.string()

                if (!response.isSuccessful) {
                    isLoading = false
                    errorMessage = "Upload error: ${response.code}"
                    withContext(Dispatchers.Main) { onDone() }
                    return@launch
                }

                val json = JSONObject(bodyString ?: "{}")
                val token = json.optString("token", "")

                if (token.isEmpty()) {
                    isLoading = false
                    errorMessage = "No token returned"
                    withContext(Dispatchers.Main) { onDone() }
                    return@launch
                }

                pollResult(token, 0, onDone)

            } catch (e: Exception) {
                isLoading = false
                errorMessage = e.localizedMessage
                withContext(Dispatchers.Main) { onDone() }
            }
        }
    }

    // Poll result
    private fun pollResult(token: String, attempt: Int, onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Wait before polling
                Thread.sleep(5000)

                val client = OkHttpClient.Builder()
                    .readTimeout(90, TimeUnit.SECONDS)
                    .build()

                val request = Request.Builder()
                    .url("https://api.tabscanner.com/api/result/$token")
                    .addHeader("apikey", TABSCANNER_API_KEY)
                    .get()
                    .build()

                val response = client.newCall(request).execute()
                val bodyString = response.body?.string()

                val json = JSONObject(bodyString ?: "{}")

                val processing = json.optBoolean("processing", false)
                val statusCode = json.optInt("status_code", -1)

                if ((processing || statusCode == 2) && attempt < 3) {
                    pollResult(token, attempt + 1, onDone)
                    return@launch
                }

                jsonResult = json.toString(4)
                errorMessage = null

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            }

            isLoading = false
            withContext(Dispatchers.Main) { onDone() }
        }
    }
}