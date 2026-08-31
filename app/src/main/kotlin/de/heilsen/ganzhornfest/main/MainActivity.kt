package de.heilsen.ganzhornfest.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Before super.onCreate(), which is what the library documents: it swaps the window over
        // to postSplashScreenTheme before the window is created. That re-reads the theme's bar
        // colours, so enableEdgeToEdge() has to stay after it.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MainScreen() }
    }
}
