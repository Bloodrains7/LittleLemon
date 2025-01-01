package com.example.littlelemon.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.ui.navigation.Onboarding

@Composable
fun Profile(navController: NavHostController, context: Context) {
    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    val firstName = remember { sharedPreferences.getString("first_name", "") ?: "" }
    val lastName = remember { sharedPreferences.getString("last_name", "") ?: "" }
    val email = remember { sharedPreferences.getString("email", "") ?: "" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with logo
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp).padding(bottom = 16.dp)
        )

        // Profile Information Header
        Text(text = "Profile information:", modifier = Modifier.padding(bottom = 16.dp))

        // Display user information
        Text(text = "First Name: $firstName", modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Last Name: $lastName", modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Email Address: $email", modifier = Modifier.padding(bottom = 16.dp))

        // Log out button
        Button(
            onClick = {
                with(sharedPreferences.edit()) {
                    clear()
                    apply()
                }
                navController.navigate(Onboarding.route) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(
        navController = rememberNavController(),
        context = LocalContext.current
    )
}