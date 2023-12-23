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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
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


//@android.support.annotation.RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayDataFromEndpoint(data: List<Chochitika>?) {
    var context = LocalContext.current
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
                            intent.putExtra("id", it[item].id)
                            intent.putExtra("address", it[item].address)
                            intent.putExtra("coordinates", it[item].coordinates)
                            intent.putExtra("description", it[item].description)
                            intent.putExtra("end_date", it[item].end_date)
                            intent.putExtra("end_time", it[item].end_time)
                            intent.putExtra("entry_fee", it[item].entry_fee)
                            intent.putExtra("location", it[item].location)
                            intent.putExtra("time", it[item].time)
                            intent.putExtra("type", it[item].type)
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
                        var output = it[item].date
                        var splitdate = output?.split(" ")
                        output = splitdate?.get(0) + " " + splitdate?.get(1) + " " + splitdate?.get(2)

                        Text(text = it[item].title ?: "No Title")
                        Text(text = "by : ${it[item].organiser ?: "No Author"}")
                        Text(text = output ?: "No Date")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var filteredData by remember { mutableStateOf<List<Chochitika>?>(null) }

    val coroutineScope = rememberCoroutineScope()
    var context = LocalContext.current
    LaunchedEffect(searchQuery) {
        coroutineScope.launch {
            filteredData = filterData(searchQuery, fetchDataFromEndpoint(context))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Zochitika App")
                },
                actions = {})
                    // Add search bar here)


        },
        content = {
            Column(Modifier.padding(it)) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    label = { Text("Search") },
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            LocalSoftwareKeyboardController
                        }
                    )
                )
                DisplayDataFromEndpoint(filteredData)
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

fun filterData(query: String, data: List<Chochitika>?): List<Chochitika>? {
    return data?.filter {
        it.title?.contains(query, ignoreCase = true) == true ||
                it.organiser?.contains(query, ignoreCase = true) == true ||
                it.date?.contains(query, ignoreCase = true) == true
    }
}

@Preview
@Composable
fun PreviewDisplayDataFromEndpoint() {
    // You can create a preview using mock data or a placeholder
    DisplayDataFromEndpoint(listOf(Chochitika("1", "Title 1", "Author 1", "2023-01-01")))
}
