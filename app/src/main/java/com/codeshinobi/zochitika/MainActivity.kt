package com.codeshinobi.zochitika

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.codeshinobi.zochitika.models.Chochitika
import com.codeshinobi.zochitika.ui.theme.ZochitikaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZochitikaTheme {
                // A surface container using the 'background' color fr
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Text(text = "Hi there!", modifier = Modifier.padding(innerPadding))
                        DisplayDataFromEndpoint()
                    }
                }
            }
        }
    }
}
@Composable
fun DisplayDataFromEndpoint() {
    val coroutineScope = rememberCoroutineScope()
//    var data by remember { mutableStateOf<List<Post>?>(null) }
    var data by remember { mutableStateOf<List<Chochitika>?>(null) }
    val context:Context = LocalContext.current
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            data = fetchDataFromEndpoint(context)
        }
    }

    Column {
        data?.forEach { post ->
            post.title?.let { Text(text = it) }
        } ?: Text(text = "Loading...")
    }
}

private val json = Json { ignoreUnknownKeys = true }

suspend fun fetchDataFromEndpoint(context:Context): List<Chochitika> {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://darlingson.pythonanywhere.com/zochitika").readText()
            Log.d("EndpointResponse", response) // Log the response
            json.decodeFromString<List<Chochitika>>(response)
        } catch (e: Exception) {
            Log.e("FetchDataError", "Error fetching data from endpoint", e)
            emptyList() // Return an empty list in case of an error
        }
    }
}

@Preview
@Composable
fun PreviewDisplayDataFromEndpoint() {
    DisplayDataFromEndpoint()
}