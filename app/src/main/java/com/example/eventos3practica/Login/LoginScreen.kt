package com.example.eventos3practica.Login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(
    onLogin: (String, String, (String) -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (email.value.isEmpty()) {
                    Text("Email")
                }
                innerTextField()
            }
        )
        BasicTextField(
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (password.value.isEmpty()) {
                    Text("Password")
                }
                innerTextField()
            }
        )
        Button(onClick = {
            onLogin(email.value, password.value) { msg ->
                message.value = msg
            }
        }) {
            Text("Login")
        }
        if (message.value.isNotEmpty()) {
            Text(message.value)
        }
    }
}