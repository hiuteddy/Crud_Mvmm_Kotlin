package com.duyle.tutorkot104

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.duyle.tutorkot104.ui.theme.TutorKOT104Theme
import com.duyle.tutorkot104.ui.theme.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TutorKOT104Theme {
                Scaffold(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {  innerPadding ->
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }
}

