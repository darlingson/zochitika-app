package com.codeshinobi.zochitika

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import com.codeshinobi.zochitika.models.Chochitika
import com.codeshinobi.zochitika.screens.ChochitikaInfo
import com.codeshinobi.zochitika.screens.navigation.BottomNavigationBar
import com.codeshinobi.zochitika.ui.theme.ZochitikaTheme
import com.codeshinobi.zochitika.viewmodels.SplashViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{viewModel.isLoading.value}
        setContent {
            ZochitikaTheme {
                // A surface container using the 'background' color fr
//                MainScreen()

                BottomNavigationBar()
            }
        }
    }
}


//@android.support.annotation.RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayDataFromEndpoint(data: List<Chochitika>?) {
    var context = LocalContext.current
    LazyColumn {
        data?.let {
            items(it.size) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(
                            animationSpec = tween(durationMillis = 1000)
                        )
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
                            intent.putExtra("poster_path", it[item].poster_path)
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

                        val sdf = SimpleDateFormat("E, dd MMM")
                        val todaysDate = sdf.format(Date())
                        
                        
                        Text(text = it[item].title ?: "No Title")
                        Text(text = "by : ${it[item].organiser ?: "No Organiser"}")
//                        Text(text = output ?: "No Date")
//                        Text(text = todaysDate)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        ) {
                            Icon(
                                painter =  painterResource(id = R.drawable.baseline_calendar_month_24),
                                contentDescription = "Date",
                                modifier = Modifier
                                    .size(34.dp)
                                    .padding(end = 8.dp),
                                tint = androidx.compose.ui.graphics.Color.Black,
                                )
                            if (output == todaysDate) {
                                Text(
                                    text = "Today",
                                    color = androidx.compose.ui.graphics.Color.Black,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
                                    fontSize = 20.sp
                                )
                            } else {
                                Text(
                                    text = output ?: "No Date",
                                    fontSize = 20.sp
                                )
                            }
                        }
                        Row {
                            Box(modifier = Modifier.weight(1f)){
//                                Text(
//                                    text = "${it[item].location ?: "No location"}"
//                                )
                                it[item].location?.let { it1 ->
                                    IconTextRow(
//                                        icon = Icons.Default.LocationOn,
                                        icon = R.drawable.baseline_location_on_24,
                                        text = it1
                                    )
                                }
                            }
                            Box(modifier = Modifier.weight(1f)){
//                                Text(
//                                    text = "${it[item].time ?: "No Time"}"
//                                )
                                IconTextRow(
                                    icon = R.drawable.baseline_access_time_24,
                                    text = it[item].time ?: "No Time")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(navController: NavController) {
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
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            LocalSoftwareKeyboardController
                        }
                    )
                )
                val items = listOf("type","Shows","Conferences")
                var expanded by remember {
                    mutableStateOf(false)
                }
                var selectedIndex by remember {
                    mutableIntStateOf(0)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false }
                ) {
                    items.forEachIndexed{ i,s ->
                        DropdownMenuItem(
                            onClick = {
                            selectedIndex = i
                            expanded = false
                        },
                            text = {
                            Text(text = s)
                        })

                    }
                }
                DisplayDataFromEndpoint(filteredData)
            }
        }
    )
}

private val json = Json { ignoreUnknownKeys = true }

suspend fun fetchDataFromEndpoint(context: Context): List<Chochitika> {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL(Endpoints.ZochitikaList).readText()
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
@Composable
fun IconTextRow(icon: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Icon
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null, // Decorative element
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )

        // Text
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun PreviewDisplayDataFromEndpoint() {
    // You can create a preview using mock data or a placeholder
    DisplayDataFromEndpoint(listOf(Chochitika("1", "Title 1", "Author 1", "2023-01-01")))
}
