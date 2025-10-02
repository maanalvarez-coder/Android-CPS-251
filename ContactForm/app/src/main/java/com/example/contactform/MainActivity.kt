package com.example.contactform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This project covers concepts from Chapter 7 lessons:
 * - "Validation" - for form validation and error handling
 * - "Managing Input State" - for state management in forms
 * - "Text Fields" - for input field styling and error states
 * - "Regular Expressions" - for email, phone, and ZIP code validation
 *
 * Students should review these lessons before starting:
 * - Validation lesson for form validation patterns
 * - Managing Input State lesson for state management
 * - Text Fields lesson for input field styling
 * - Regular Expressions lesson for validation patterns
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactValidatorApp()
            }
        }
    }
}

@Composable
fun ContactValidatorApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ContactForm()
    }
}

@Composable
fun ContactForm() {
    // TODO: Create state variables for form fields
    // Hint: You need variables for:
    // - name (string for user's name)
    var name by remember { mutableStateOf("") }
    // - email (string for email address)
    var email by remember { mutableStateOf("") }
    // - phone (string for phone number)
    var phone by remember {mutableStateOf("")}
    // - zipCode (string for ZIP code)
    var zip by remember { mutableStateOf("") }


    // TODO: Create validation state variables
    // Hint: You need boolean variables for:
    // - isNameValid, isEmailValid, isPhoneValid, isZipValid
    var isNameValid by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPhoneValid by remember { mutableStateOf(false) }
    var isZipValid by remember { mutableStateOf(false) }
    // Use remember and mutableStateOf for each
    // See "Managing Input State" lesson for examples of validation state management

    // TODO: Create submitted information state variable
    var info by remember { mutableStateOf("") }
    // Hint: You need a variable for: submittedInfo (string for displaying submitted data)
    // Use remember and mutableStateOf

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
                // See "Text Fields" lesson for examples of shape customization
            )
            // TODO: Add horizontal and vertical padding with a reasonable dp value
            // See "Text Fields" lesson for examples of padding
            .padding(
                horizontal =  24.dp,
        vertical = 24.dp
    ),
    // TODO: Arrange items vertically with a reasonable dp spacing
    // See "Text Fields" lesson for examples of vertical arrangement
    verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // TODO: Call the NameField composable here
        NameField(name, isNameValid, onValueChange =  {name = it
        isNameValid = it.isNotEmpty()})
        // Pass the name state, validation state, and onValueChange lambda
        // See "Text Fields" lesson for examples of error state styling

        // TODO: Call the EmailField composable here
        EmailField(email, isEmailValid, onValueChange = {
            email = it
            isEmailValid = it.isNotEmpty() && validateEmail(it)

        })
        // Pass the email state, validation state, and onValueChange lambda
        // See "Validation" lesson for email validation examples

        // TODO: Call the PhoneField composable here
        PhoneField(phone, isPhoneValid, onValueChange = {
            phone = it
            isPhoneValid = it.isNotEmpty() && validatePhone(it)
        })
        // Pass the phone state, validation state, and onValueChange lambda
        // See "Regular Expressions" lesson for phone number validation patterns

        // TODO: Call the ZipCodeField composable here
        ZipCodeField(zip, isZipValid, onValueChange = {
            zip = it
            isZipValid = it.isNotEmpty() && validateZip(it)
        })
        // Pass the zipCode state, validation state, and onValueChange lambda
        // See "Regular Expressions" lesson for ZIP code validation examples

        // TODO: Create the Submit button
        // Use Button composable with enabled state based on all validations
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = isPhoneValid && isNameValid && isEmailValid && isZipValid,
            onClick = {
                info =
                    "Name: $name \n" +
                    "Email: $email \n" +
                    "Phone: $phone\n" +
                    "Zip Code: $zip\n"
            }

        ) {Text("Submit")}
        // The button should only be enabled when all fields are valid and not empty
        // See "Validation" lesson for examples of complex button state management

        // TODO: Add display for submitted information
        Text(info)
    }
}

@Composable
fun NameField(
    name: String,
    isNameValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the name input field
    OutlinedTextField(
        value = name,
        onValueChange,
        label = { Text("Name") },
        modifier = Modifier.fillMaxWidth(),

    )
    // See "Text Fields" lesson for complete examples of error state styling
}

@Composable
fun EmailField(
    email: String,
    isEmailValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the email input field
    OutlinedTextField(
        value = email,
        onValueChange,
        isError =  !validateEmail(email) && email.isNotEmpty(),
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (email.isNotEmpty() && !isEmailValid) {
                Text("Please enter a valid email", color = Color.Red)
            }
        }

    )
    // See "Validation" lesson for email validation examples with regex
}

@Composable
fun PhoneField(
    phone: String,
    isPhoneValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the phone input field
    // Use OutlinedTextField with:
    OutlinedTextField(
        value = phone,
        onValueChange,
        isError =  !validatePhone(phone) && phone.isNotEmpty(),
        label = { Text("Phone") },
        modifier = Modifier.fillMaxWidth(),
        supportingText = {
            if (phone.isNotEmpty() && !isPhoneValid) {
                Text("Please enter a valid phone #", color = Color.Red)
            }
        }
    )
    // See "Regular Expressions" lesson for phone number validation patterns and examples
}

@Composable
fun ZipCodeField(
    zipCode: String,
    isZipValid: Boolean,
    onValueChange: (String) -> Unit
) {
    // TODO: Create the ZIP code input field
    // Use OutlinedTextField with:
    OutlinedTextField(
        value = zipCode,
        onValueChange,
        isError =  !validateZip(zipCode) && zipCode.isNotEmpty(),
        label = { Text("Zip Code") },
        modifier = Modifier.fillMaxWidth(),
                supportingText = {
            if (zipCode.isNotEmpty() && !isZipValid) {
                Text("Please enter a valid zip", color = Color.Red)
            }
        }
    )
    // See "Regular Expressions" lesson for ZIP code validation examples
}

// TODO: Create validation functions using regex
// Hint: You need three functions:
// 1. validateEmail() - checks email format using regex pattern
fun validateEmail(
    text: String = ""
): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$".toRegex()
    return text.matches(emailRegex)
}
//
// 2. validatePhone() - checks for phone numbers like 123-456-7890 or 123/456/7890
fun validatePhone(
    text: String = ""
): Boolean {
    val phoneRegex = "^(\\+?\\d{1,2}\\s?)?(\\(?\\d{3}\\)?[\\s.-]?)?\\d{3}[\\s.-]?\\d{4}$".toRegex()
    return text.matches(phoneRegex)

}
// 3. validateZipCode() - checks for exactly 5 digits
fun validateZip(
    text: String = ""
): Boolean {
    val zipRegex = "^\\d{5}(-\\d{4})?\$".toRegex()
    return text.matches(zipRegex)
}
// Use the .matches() function with regex patterns
// See "Regular Expressions" lesson for complete regex examples and validation patterns

// You will need to enable the submit button when all the fields are valid:
//
// See "Validation" lesson for detailed examples of complex button state management
//when button is clicked and all fields are valid and not empty, the submitted information should be displayed
//in a text field below the button.

/**
 * Preview for Android Studio's design view.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactValidatorAppPreview() {
    ContactValidatorApp()
}
//Show me your ContactForm function and point to where you declare your state variables for form fields. Explain why you need separate variables for the input values and validation states.  Of if you did not have seperate variables why not?
//Show me one of your input fields (like NameField or EmailField) and point to the error part. Explain how it creates visual feedback for users
//Point to your validateEmail function and explain the regex pattern you used. What would happen if you changed the pattern to something simpler like just checking for '@' and '.' characters? Why is the current pattern more robust?
//Point to your Submit button and explain the enabled condition. What would happen if you simplified the condition to just check if all fields are not empty without validation?
//Point to your Submit button and explain how it gets the text to appear below it when clicked.?