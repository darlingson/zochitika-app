package com.codeshinobi.zochitika.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codeshinobi.zochitika.screens.ui.theme.ZochitikaTheme

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
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
                .fillMaxWidth())
        imageFromURL()
    }

}
@Composable
fun imageFromURL() {
    // on below line we are creating a column,
    Column(
        // in this column we are adding modifier
        // to fill max size, mz height and max width
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            // on below line we are adding
            // padding from all sides.
            .padding(10.dp),
        // on below line we are adding vertical
        // and horizontal arrangement.
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are adding image for our image view.
        Image(
            // on below line we are adding the image url
            // from which we will  be loading our image.
            painter = rememberAsyncImagePainter("https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png"),

            // on below line we are adding content
            // description for our image.
            contentDescription = "gfg image",

            // on below line we are adding modifier for our
            // image as wrap content for height and width.
            modifier = Modifier
                .wrapContentSize()
                .wrapContentHeight()
                .wrapContentWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZochitikaTheme {
        ChochitikaInfoMain("Android")
    }
}