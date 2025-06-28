package com.example.iconchanger

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.iconchanger.ui.theme.IconChangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IconChangerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

private fun switchIcon(enableAlias: String, disableAlias: String,context:Context) {
    val pm = context.packageManager

    val enableComponent = ComponentName(context, "com.example.iconchanger.$enableAlias")
    val disableComponent = ComponentName(context, "com.example.iconchanger.$disableAlias")

    pm.setComponentEnabledSetting(
        enableComponent,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )

    pm.setComponentEnabledSetting(
        disableComponent,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var isDogSelected by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Icon Switcher", style = TextStyle(textAlign = TextAlign.Center), modifier = Modifier.fillMaxWidth()) })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dog),
                contentDescription = "Dog",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(100.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "Cat",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(100.dp)
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (isDogSelected) "Dog Selected" else "Cat Selected")
                Switch(
                    checked = isDogSelected,
                    onCheckedChange = {
                        isDogSelected = it
                        if (isDogSelected) {
                            switchIcon("DogIcon", "CatIcon", context)
                        } else {
                            switchIcon("CatIcon", "DogIcon", context)
                        }
                    }
                )
            }
        }
    }
}

