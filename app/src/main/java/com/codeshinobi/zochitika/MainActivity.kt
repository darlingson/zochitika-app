package com.codeshinobi.zochitika

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codeshinobi.zochitika.ui.theme.ZochitikaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZochitikaTheme {
                // A surface container using the 'background' color fr
                Scaffold { innerPadding ->
                    Text(text = "Hi there!", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun DisplayDataFromEndpoint() {
    val coroutineScope = rememberCoroutineScope()
    var data by remember { mutableStateOf<List<Post>?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            data = fetchDataFromEndpoint()
        }
    }

    Column {
        data?.forEach { post ->
            Text(text = post.title)
        } ?: Text(text = "Loading...")
    }
}

suspend fun fetchDataFromEndpoint(): List<Post> {
    return withContext(Dispatchers.IO) {
        val response = URL("https://jsonplaceholder.typicode.com/posts").readText()
        Json { ignoreUnknownKeys = true }.decodeFromString<List<Post>>(response)
    }
}

@Preview
@Composable
fun PreviewDisplayDataFromEndpoint() {
    DisplayDataFromEndpoint()
}