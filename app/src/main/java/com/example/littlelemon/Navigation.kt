package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun LittleLemonNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = if (LittleLemonPreferences.isUserRegistered()) Home.route else Onboarding.route
    NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            HomeScreen(navController)
        }
        composable(Profile.route) {
            ProfileScreen(navController)
        }
    }
}