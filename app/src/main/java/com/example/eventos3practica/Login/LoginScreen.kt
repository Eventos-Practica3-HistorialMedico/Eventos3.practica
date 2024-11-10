package com.example.eventos3practica.Login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    onLogin: (String, String, (String) -> Unit) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicTextField(
                value = username.value,
                onValueChange = { username.value = it },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (username.value.isEmpty()) {
                        Text("Usuario")
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
                        Text("Contraseña")
                    }
                    innerTextField()
                }
            )
            Button(onClick = {
                onLogin(username.value, password.value) { msg ->
                    message.value = msg
                    if (msg == "Login exitoso") {
                        navController.navigate("historial/${username.value}")
                    }
                }
            }) {
                Text("Login")
            }
            if (message.value.isNotEmpty()) {
                Text(message.value)
            }
        }
    }
}