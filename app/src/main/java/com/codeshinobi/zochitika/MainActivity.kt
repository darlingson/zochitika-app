package com.codeshinobi.zochitika

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.codeshinobi.zochitika.ui.theme.ZochitikaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
