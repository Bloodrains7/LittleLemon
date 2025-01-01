import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R

@Composable
fun OnBoarding(navController: NavHostController, context: Context) {
    // State holders for TextFields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header section with logo
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp).padding(bottom = 16.dp)
        )

        // Prompt for user input
        Text(text = "Let's get to know you", modifier = Modifier.padding(bottom = 16.dp))

        // TextFields for user input
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Register Button
        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    message = "Registration unsuccessful. Please enter all data."
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                } else {
                    // Save data to SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putString("first_name", firstName)
                        putString("last_name", lastName)
                        putString("email", email)
                        apply()
                    }
                    message = "Registration successful!"
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                    // Navigate to Home screen
                    navController.navigate("Home")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        // Feedback message
        if (message.isNotBlank()) {
            Text(text = message, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    OnBoarding(
        navController = rememberNavController(),
        context = LocalContext.current
    )
}
