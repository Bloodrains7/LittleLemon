package com.example.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ProfileScreen(navigation: NavHostController, modifier: Modifier = Modifier) {
    ProfileScreenContent(
        modifier,
        firstName = LittleLemonPreferences.getUserFirstName(),
        lastName = LittleLemonPreferences.getUserLastName(),
        email = LittleLemonPreferences.getUserEmail(),
        onBack = {
            navigation.navigateUp()
        },
        onLogOut = {
            LittleLemonPreferences.clearUserData()
            navigation.navigate(Onboarding.route)
        })
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    firstName: String = "",
    lastName: String = "",
    email: String = "",
    onLogOut: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Scaffold(modifier = modifier, topBar = {
        LittleLemonTopAppBar(navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 50.dp)
            ) {
                Text("Personal Information", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(20.dp))
                Text("First Name", style = MaterialTheme.typography.labelSmall)
                Text(
                    firstName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text("Last Name", style = MaterialTheme.typography.labelSmall)
                Text(
                    lastName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text("Email", style = MaterialTheme.typography.labelSmall)
                Text(
                    email,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            ElevatedButton(
                onClick = onLogOut,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = contentColorFor(MaterialTheme.colorScheme.secondaryContainer)
                )
            ) {
                Text("Log Out")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    LittleLemonTheme {
        ProfileScreenContent(
            firstName = "John",
            lastName = "Wick",
            email = "john.wick@gmail.com"
        )
    }
}