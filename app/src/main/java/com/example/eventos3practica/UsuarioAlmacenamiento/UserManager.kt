package com.example.eventos3practica.UsuarioAlmacenamiento

import android.content.Context

object UserManager {
    private lateinit var secureStorage: SecureStorage

    fun init(context: Context) {
        secureStorage = SecureStorage(context)
    }

    fun addUser(user: User) {
        secureStorage.saveUser(user)
    }

    fun getUser(email: String, password: String): User? {
        return secureStorage.getUser(email, password)
    }
}