package com.example.eventos3practica.Login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventos3practica.Historial.HistorialScreen
import com.example.eventos3practica.UsuarioAlmacenamiento.UserManager
import com.example.eventos3practica.ui.theme.Eventos3practicaTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserManager.init(this) // Inicializa UserManager
        setContent {
            Eventos3practicaTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                onLogin = { username, password, showMessage ->
                                    val user = UserManager.getUserByUsername(username, password)
                                    if (user != null) {
                                        showMessage("Login exitoso")
                                    } else {
                                        showMessage("Usuario no encontrado")
                                    }
                                },
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("historial/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username") ?: ""
                            HistorialScreen(email = username, modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}