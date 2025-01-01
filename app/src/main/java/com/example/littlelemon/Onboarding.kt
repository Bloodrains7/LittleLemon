package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navigation: NavHostController, modifier: Modifier = Modifier) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            LittleLemonTopAppBar()

        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Let's get to know you",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.weight(1F),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.wrapContentHeight()) {
                    Text(
                        "Personal Information",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 50.dp)
                    )
                    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 50.dp)) {
                        Text(
                            "First Name",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            firstName,
                            onValueChange = { firstName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                        )

                        Text(
                            "Last Name",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            lastName,
                            onValueChange = { lastName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                        )

                        Text(
                            "Email",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            email,
                            onValueChange = { email = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                }

                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    onClick = {
                        if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                            scope.launch { snackbarHostState.showSnackbar("Registration unsuccessful. Please enter all data.") }
                            return@ElevatedButton
                        } else {
                            LittleLemonPreferences.saveUserData(firstName, lastName, email)
                            navigation.navigate(Home.route) {
                                popUpTo(Onboarding.route) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = contentColorFor(MaterialTheme.colorScheme.secondaryContainer)
                        )
                ) {
                    Text("Register")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Onboarding(rememberNavController())
    }
}