package com.codeshinobi.zochitika.screens.navigation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.codeshinobi.zochitika.Endpoints
import com.codeshinobi.zochitika.IconTextRow
import com.codeshinobi.zochitika.R
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
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun NearMeScreen(navController: NavController, viewModel: ProfileViewModel) {
    ZochitikaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .clip(MaterialTheme.shapes.large)
                ) {
                    Column {
                        val savedName = viewModel.name
                        val savedLoc = viewModel.loc

                        Text("Hello $savedName!")
                        Text("Your current Saved Location is $savedLoc")
                        NearmeMain(viewModel)
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NearmeMain(viewModel: ProfileViewModel){
    val savedLoc = viewModel.loc
    var searchQuery = savedLoc
    var filteredData by remember { mutableStateOf<List<Chochitika>?>(null) }

    val coroutineScope = rememberCoroutineScope()
    var context = LocalContext.current
    LaunchedEffect(searchQuery) {
        coroutineScope.launch {
            filteredData = fetchNearByDataFromEndpoint(context, searchQuery)
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
                DisplayNearByEventsFromEndpoint(filteredData)
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayNearByEventsFromEndpoint(data: List<Chochitika>?) {
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
                            ContextCompat.startActivity(context, intent, null)
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
                                tint = Color.Black,
                            )
                            if (output == todaysDate) {
                                Text(
                                    text = "Today",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
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

private val json = Json { ignoreUnknownKeys = true }

suspend fun fetchNearByDataFromEndpoint(context: Context,location:String): List<Chochitika> {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL(Endpoints.zochitika_near_me + location).readText()
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