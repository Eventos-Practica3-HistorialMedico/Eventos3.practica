package com.example.eventos3practica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.eventos3practica.Login.LoginScreen
import com.example.eventos3practica.UsuarioAlmacenamiento.UserManager
import com.example.eventos3practica.ui.theme.Eventos3practicaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserManager.init(this) // Inicializa UserManager
        setContent {
            Eventos3practicaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        onLogin = { email, password, showMessage ->
                            val user = UserManager.getUser(email, password)
                            if (user != null) {
                                showMessage("Login exitoso")
                            } else {
                                showMessage("Usuario no encontrado")
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}