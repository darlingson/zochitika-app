package com.codeshinobi.zochitika.screens.navigation

import android.content.Context
import android.content.SharedPreferences
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.codeshinobi.zochitika.ui.theme.ZochitikaTheme
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
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
                        onClick = {
                            viewModel.saveData(name, loc)
                        },
                        Modifier.fillMaxWidth(),
                    ) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.padding(top=16.dp, bottom = 16.dp))
                    Text(text = "Current Info")

                    val savedName = viewModel.name
                    val savedLoc = viewModel.loc

                    Text("Saved Name: $savedName")
                    Text("Saved Location: $savedLoc")
                }
            }
        }
    }
}


class ProfileViewModel(private val context: Context) : ViewModel() {

    private val sharedPreferencesKey = "profile_data"
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
    }

    var name by mutableStateOf(sharedPreferences.getString("name", "") ?: "")
        private set

    var loc by mutableStateOf(sharedPreferences.getString("loc", "") ?: "")
        private set

    fun saveData(name: String, loc: String) {
        viewModelScope.launch {
            sharedPreferences.edit().apply {
                putString("name", name)
                putString("loc", loc)
            }.apply()
            this@ProfileViewModel.name = name
            this@ProfileViewModel.loc = loc
        }
    }
}