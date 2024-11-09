package com.example.eventos3practica.UsuarioAlmacenamiento

import android.content.Context

object UserManager {
    private lateinit var secureStorage: SecureStorage

    fun init(context: Context) {
        secureStorage = SecureStorage(context)
        addDefaultUser()
    }

    private fun addDefaultUser() {
        val defaultUser = User(
            email = "default@example.com",
            user = "user",
            password = "user",
            phone = "1234567890",
            address = "123 Default St",
            historialMedico = null
        )
        addUser(defaultUser)
    }

    fun addUser(user: User) {
        secureStorage.saveUser(user)
    }

    fun getUserByUsername(username: String, password: String): User? {
        return secureStorage.getUserByUsername(username, password)
    }
}