package com.codeshinobi.zochitika.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.codeshinobi.zochitika.screens.ui.theme.ZochitikaTheme


class ChochitikaInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            intent.getStringExtra("text")
            ZochitikaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChochitikaInfoMain("Android",intent = intent)
                }
            }
        }
    }
}

@Composable
fun ChochitikaInfoMain(name: String, modifier: Modifier = Modifier, intent: Intent) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
                .fillMaxWidth()
        )

        imageFromURL()
        EventInfo(intent)

    }

}
@Composable
fun EventInfo(intent: Intent) {
    var title :String = intent.getStringExtra("title").toString()
    var organiser :String = intent.getStringExtra("organiser").toString()
    var date :String = intent.getStringExtra("date").toString()
    var address:String = intent.getStringExtra("address").toString()
    var coordinates:String = intent.getStringExtra("coordinates").toString()
    var description:String = intent.getStringExtra("description").toString()
    var end_date:String = intent.getStringExtra("end_date").toString()
    var end_time:String = intent.getStringExtra("end_time").toString()
    var entry_fee:String = intent.getStringExtra("entry_fee").toString()
    var location:String = intent.getStringExtra("location").toString()
    var time:String = intent.getStringExtra("time").toString()
    var type:String = intent.getStringExtra("type").toString()
    var id:String = intent.getStringExtra("id").toString()
    Column {
        Text(text = title)
        Text(text = "by : ${organiser}")
        Text(text = "at : ${location}")
        Text(text = "on : ${date}")
        Text(text = "Start Time : ${time}")
        Text(text = "address : ${address}")
        GMButton(title = "view location")
    }
}
@Composable
fun GMButton(title: String) {
    val context = LocalContext.current
    Button(onClick = {
        val gmmIntentUri = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        }
    }) {
        Text("Open Google Maps")
    }
}
@Composable
fun imageFromURL() {
    // on below line we are creating a column,
    Column(
        // in this column we are adding modifier
        // to fill max size, mz height and max width
        modifier = Modifier
//            .fillMaxSize()
            .height(200.dp)
            .fillMaxWidth()
            // on below line we are adding
            // padding from all sides.
            .padding(10.dp),
        // on below line we are adding vertical
        // and horizontal arrangement.
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // on below line we are adding image for our image view.
        Image(
            // on below line we are adding the image url
            // from which we will  be loading our image.
            painter = rememberAsyncImagePainter("https://picsum.photos/600?blur=2"),

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
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ZochitikaTheme {
//        ChochitikaInfoMain("Android",intent = intent)
//    }
//}