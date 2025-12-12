package com.example.module37_8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.module37_8.ui.theme.Module378Theme
import com.example.todolist.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Module378Theme {
                Surface {
                    // Навигация приложения
                    NavGraph(applicationContext)
                }
            }
        }
    }
}
