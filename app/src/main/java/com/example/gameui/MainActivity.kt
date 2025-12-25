package com.example.gameui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.gameui.ui.theme.GameColors
import com.example.gameui.ui.theme.GameUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = GameColors.BackgroundLight
                ) {
                    NavGraph()
                }
            }
        }
    }
}
