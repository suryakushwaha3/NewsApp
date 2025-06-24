package com.example.news

import HomeUI
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.Navigation
import com.example.news.MyViewModel.MyViewModel
import com.example.news.navigation.NavigationGraph
import com.example.news.ui.theme.NewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Remember the ViewModel instance across recompositions if needed
            val viewModel = remember { MyViewModel() }

            NewsTheme {
                    NavigationGraph(viewModel)
            }
        }
    }
}

