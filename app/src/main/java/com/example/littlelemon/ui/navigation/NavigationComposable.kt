package com.example.littlelemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.screen.Home;
import com.example.littlelemon.ui.screen.Profile;
import OnBoarding;

@Composable
fun NavigationComposable() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Profile.route
    ) {
        composable(Onboarding.route) { OnBoarding(navController = navController, context = LocalContext.current) }
        composable(Home.route) { Home() }
        composable(Profile.route) { Profile(navController = navController, context = LocalContext.current) }
    }
}