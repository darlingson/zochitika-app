package com.codeshinobi.zochitika.screens.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codeshinobi.zochitika.ui.theme.ZochitikaTheme

@Composable
fun ProfileScreen(navController: NavController) {
    ZochitikaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            )  {
                var name by rememberSaveable { mutableStateOf("") }
                var loc by rememberSaveable {
                    mutableStateOf("")
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    TextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        Modifier.fillMaxWidth(),
                        label = { Text("Name") }
                    )
                    TextField(
                        value = loc,
                        onValueChange = {
                            loc = it
                        },
                        Modifier.fillMaxWidth(),
                        label = { Text("Location") }
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        Modifier.fillMaxWidth(),
                    ) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.padding(top=16.dp, bottom = 16.dp))
                    Text(text = "Current Info")

                }
            }
        }
    }
}