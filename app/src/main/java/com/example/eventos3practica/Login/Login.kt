package com.example.eventos3practica.Login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.eventos3practica.UsuarioAlmacenamiento.UserManager


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLogin = { email: String, password: String, showMessage ->
                    val user = UserManager.getUser(email, password)
                    if (user != null) {
                        showMessage("Login exitoso")
                    } else {
                        showMessage("Usuario no encontrado")
                    }
                }
            )
        }
    }
}