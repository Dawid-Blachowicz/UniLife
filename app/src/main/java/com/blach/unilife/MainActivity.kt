package com.blach.unilife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.blach.unilife.navigation.Router
import com.blach.unilife.navigation.Screen
import com.blach.unilife.screens.LoginScreen
import com.blach.unilife.screens.RegisterScreen
import com.blach.unilife.ui.theme.UniLifeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniLifeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Crossfade(targetState = Router.currentScreen, label = "screens_crossfade") { currentState ->
                        when(currentState.value) {
                            is Screen.RegisterScreen -> {
                                RegisterScreen()
                            }
                            is Screen.LoginScreen -> {
                                LoginScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UniLifeTheme {
        Greeting("Android")
    }
}