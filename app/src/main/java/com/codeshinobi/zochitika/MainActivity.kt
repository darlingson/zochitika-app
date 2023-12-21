package com.codeshinobi.zochitika

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.codeshinobi.zochitika.models.Chochitika
import com.codeshinobi.zochitika.screens.ChochitikaInfo
import com.codeshinobi.zochitika.ui.theme.ZochitikaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.net.URL

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZochitikaTheme {
                // A surface container using the 'background' color fr
                MainScreen()
            }
        }
    }
}

@Composable
fun DisplayDataFromEndpoint() {
    val coroutineScope = rememberCoroutineScope()
    var data by remember { mutableStateOf<List<Chochitika>?>(null) }
    val context: Context = LocalContext.current
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            data = fetchDataFromEndpoint(context)
        }
    }

    LazyColumn {
        data?.let {
            items(it.size) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Handle click event here
                            Log.d("CardClick", "Card clicked: ${it[item].title}")
                            val intent = Intent(context, ChochitikaInfo::class.java)
                            intent.putExtra("title", it[item].title)
                            intent.putExtra("organiser", it[item].organiser)
                            intent.putExtra("date", it[item].date)
                            startActivity(context, intent, null)
                                   },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = it[item].title ?: "No Title")
                        Text(text = "by : ${it[item].organiser ?: "No Author"}")
                        Text(text = it[item].date ?: "No Date")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Zochitika App")
                },
                actions = {
                    // Add any actions here
                }
            )
        },
        content = {
            Column(Modifier.padding(it)) {
                Text(text = "Zochitila",)
                DisplayDataFromEndpoint()
            }
        }
    )
}

private val json = Json { ignoreUnknownKeys = true }

suspend fun fetchDataFromEndpoint(context: Context): List<Chochitika> {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://darlingson.pythonanywhere.com/zochitika").readText()
            Log.d("EndpointResponse", response) // Log the response
            json.decodeFromString<List<Chochitika>>(response)
        } catch (e: FileNotFoundException) {
            Log.e("FetchDataError", "File not found at the specified URL", e)
            emptyList() // Return an empty list in case of a 404 error
        } catch (e: Exception) {
            Log.e("FetchDataError", "Error fetching data from endpoint", e)
            emptyList() // Return an empty list for other errors
        }
    }
}

@Preview
@Composable
fun PreviewDisplayDataFromEndpoint() {
    DisplayDataFromEndpoint()
}
