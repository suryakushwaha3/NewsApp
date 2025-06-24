package com.example.news.navigation

import DetailScreen
import HomeUI
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.news.MyViewModel.MyViewModel



@Composable
fun NavigationGraph(viewModel: MyViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeUI(navController = navController, viewModel = viewModel)
        }

        composable("details") {
            DetailScreen(viewModel = viewModel)
        }
    }
}
