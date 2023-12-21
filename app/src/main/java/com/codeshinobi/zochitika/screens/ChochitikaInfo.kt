package com.codeshinobi.zochitika.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.codeshinobi.zochitika.screens.ui.theme.ZochitikaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class ChochitikaInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZochitikaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChochitikaInfoMain("Android")
                }
            }
        }
    }
}

@Composable
fun ChochitikaInfoMain(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
@Composable
fun LoadImage(url: String) {
    val bitmapState: MutableState<Bitmap?> = remember { mutableStateOf(null) }

    LaunchedEffect(url) {
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        try {
            urlConnection.doInput = true
            urlConnection.connect()
            val input = BufferedInputStream(urlConnection.inputStream)
            val bitmap = BitmapFactory.decodeStream(input)
            withContext(Dispatchers.Main) {
                bitmapState.value = bitmap
            }
        } catch (e: Exception) {
            // Handle the exception
        } finally {
            urlConnection.disconnect()
        }
    }
}

@Composable
fun DisplayImageFromUrl(url: String) {
    val bitmapState = LoadImage(url)
    bitmapState.value?.let { bitmap ->
        Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZochitikaTheme {
        ChochitikaInfoMain("Android")
    }
}